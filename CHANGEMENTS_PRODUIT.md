# Changements sur Produit - Avant / Après

## Commit du professeur : `ffd85bc` - "ResponseEntiy et Spring data jpa"

---

## 1. Repository : Passage à Spring Data JPA

### Avant
```java
public interface ProductRepository {
    void save(Produit product);
    List<Produit> findAll();
    Produit findById(Long id);
    void delete(Long id);
}
```
+ Implémentation manuelle dans `ProductRepositoryImpl.java` (46 lignes)

### Après
```java
public interface ProductRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByLibelle(String libelle);
    List<Produit> findByLibelleContainingAndPrixGreaterThan(String libelle, double prix);
}
```
- **Suppression** de `ProductRepositoryImpl.java` (plus besoin d'implémentation manuelle)
- **Héritage** de `JpaRepository` qui fournit automatiquement : `save()`, `findAll()`, `findById()`, `deleteById()`, etc.
- **Nouvelles méthodes de requête** : `findByLibelle()`, `findByLibelleContainingAndPrixGreaterThan()`

**Pitch :** Avant, on écrivait tout le code SQL et la logique d'accès aux données nous-mêmes dans une classe d'implémentation. Maintenant, avec Spring Data JPA, on étend simplement `JpaRepository` et les méthodes CRUD de base sont générées automatiquement. On peut même créer des requêtes personnalisées juste en nommant les méthodes correctement — fini le code JDBC/EntityManager répétitif.

---

## 2. Service : Retour de `Optional` et `Produit`

### Avant
```java
public interface ProductService {
    void save(Produit product);
    List<Produit> findAll();
    Produit findById(Long id);
    void delete(Long id);
}
```

### Après
```java
public interface ProductService {
    Produit save(Produit product);           // retourne l'entité sauvegardée
    List<Produit> findAll();
    Optional<Produit> findById(Long id);     // retourne Optional (nullable safe)
    void delete(Long id);
}
```

**Changements dans `ProductServiceImpl` :**
- `save()` retourne maintenant `repository.save(product)` au lieu de `void`
- `findById()` retourne `Optional<Produit>` via `repository.findById(id)`
- `delete()` utilise `repository.deleteById(id)` au lieu de `repository.delete(id)`

**Pitch :** Avant, `save()` ne retournait rien et `findById()` pouvait renvoyer `null` silencieusement, causant des `NullPointerException` difficiles à debugger. Maintenant, `save()` retourne l'entité persistée (utile pour récupérer l'ID généré) et `findById()` retourne un `Optional<Produit>` qui force le développeur à gérer explicitement le cas où l'entité n'existe pas — plus de NPE surprises.

---

## 3. REST Controller : Utilisation de `ResponseEntity`

### Avant
```java
@GetMapping
public List<Produit> getList() { ... }

@PostMapping
public Produit save(@RequestBody Produit produit) { ... }

@DeleteMapping("/delete/{id}")
public void delete(@PathVariable Long id) { ... }
```

### Après
```java
@GetMapping
public List<Produit> getList() { ... }

@PostMapping
public ResponseEntity<Produit> save(@RequestBody Produit produit) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(produit));
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    Optional<Produit> produit = productService.findById(id);
    if (!produit.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    productService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
}

@GetMapping("/{id}")
public ResponseEntity<Produit> getById(@PathVariable Long id) {
    Optional<Produit> produit = productService.findById(id);
    if (!produit.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(200).body(produit.get());
}

@PutMapping("/edit/{id}")
public ResponseEntity<String> edit(@PathVariable Long id, @RequestBody Produit produit) {
    Optional<Produit> produitUpd = productService.findById(id);
    if (!produitUpd.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    produitUpd.get().setLibelle(produit.getLibelle());
    produitUpd.get().setPrix(produit.getPrix());
    productService.save(produitUpd.get());
    return ResponseEntity.status(200).body("produit modifie avec succes");
}
```

**Ajouts :**
- `ResponseEntity` pour tous les endpoints (meilleure gestion des codes HTTP)
- `@GetMapping("/{id}")` pour récupérer un produit par ID
- `@PutMapping("/edit/{id}")` pour modifier un produit
- Gestion des cas d'erreur (404 NOT_FOUND)

**Pitch :** Avant, le REST controller renvoyait des objets bruts ou du `void`, sans contrôle sur le statut HTTP — le client ne savait pas si l'opération avait réussi ou échoué. Maintenant, chaque endpoint retourne un `ResponseEntity` qui encapsule à la fois le corps de la réponse et le statut HTTP approprié (201 pour création, 404 si non trouvé, 200 pour succès). C'est une API REST professionnelle et conforme aux standards.

---

## 4. Controller Web : Légère modification

### Avant
```java
Produit produit = productService.findById(id);
```

### Après
```java
Produit produit = productService.findById(id).get();
```
Adaptation au retour `Optional<Produit>` du service.

**Pitch :** Avant, le controller web appelait directement `findById()` qui retournait un `Produit` (potentiellement `null`). Maintenant, il faut extraire la valeur de l'`Optional` avec `.get()`. C'est un petit changement qui reflète la nouvelle philosophie : on ne ignore plus la possibilité qu'un produit n'existe pas.

---

## 5. Configuration : Activation de JPA

### Avant
```java
@Configuration
@EnableTransactionManagement
public class AppConfig { ... }
```

### Après
```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "diti.repository")
public class AppConfig { ... }
```
Ajout de `@EnableJpaRepositories` pour activer Spring Data JPA.

**Pitch :** Avant, Spring ne savait pas qu'il devait scanner les repositories pour créer les beans JPA. Maintenant, l'annotation `@EnableJpaRepositories` dit à Spring : "regarde dans le package `diti.repository` et crée automatiquement les implémentations de toutes les interfaces qui étendent `JpaRepository`". C'est la clé de voûte de Spring Data JPA.

---

## 6. POM XML : Ajout de Spring Data JPA

### Avant
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
    <version>6.1.14</version>
</dependency>
```

### Après
```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
    <version>2.7.18</version>
</dependency>
```

**Pitch :** Avant, on utilisait `spring-orm` pour faire du JPA "à la main" avec `EntityManager`. Maintenant, on dépend de `spring-data-jpa` qui apporte toute la magie : création automatique des repositories, requêtes par méthode dérivée, pagination, tri, etc. C'est un changement de paradigme — on passe de l'ORM bas niveau à un framework de haut niveau qui booste la productivité.

---

## Résumé des bénéfices

| Aspect | Avant | Après |
|--------|-------|-------|
| Repository | Implémentation manuelle | Spring Data JPA (automatique) |
| CRUD | Code répétitif | Méthodes générées automatiquement |
| Requêtes | SQL manuel | Méthodes dérivées (`findByLibelle`, etc.) |
| API REST | Retours simples | `ResponseEntity` avec codes HTTP appropriés |
| Null safety | Risque de NPE | `Optional<Produit>` |
| Configuration | `spring-orm` | `spring-data-jpa` |
