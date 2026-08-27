package diti.REST;

import diti.entity.TypeProduit;
import diti.exception.ResourceNotFoundException;
import diti.service.TypeProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/type-produits")
@Tag(name = "Types de Produits", description = "API de gestion des types de produits")
public class TypeProduitRestController {

    @Autowired
    private TypeProduitService typeProduitService;

    @GetMapping
    @Operation(summary = "Lister tous les types de produits", description = "Retourne la liste de tous les types de produits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public List<TypeProduit> getList(){
        return typeProduitService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un type de produit par ID", description = "Retourne un type de produit par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de produit trouvé"),
            @ApiResponse(responseCode = "404", description = "Type de produit non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<TypeProduit> getById(@Parameter(description = "Identifiant du type de produit", example = "1") @PathVariable Long id){
        TypeProduit typeProduit = typeProduitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun type de produit trouve avec l'id " + id));
        return ResponseEntity.status(200).body(typeProduit);
    }

    @PostMapping
    @Operation(summary = "Créer un type de produit", description = "Crée un nouveau type de produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Type de produit créé avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<TypeProduit> save(@Parameter(description = "Données du type de produit à créer") @RequestBody TypeProduit typeProduit){
        return ResponseEntity.status(HttpStatus.CREATED).body(typeProduitService.save(typeProduit));
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Modifier un type de produit", description = "Met à jour un type de produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de produit modifié avec succès"),
            @ApiResponse(responseCode = "404", description = "Type de produit non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<String> edit(@Parameter(description = "Identifiant du type de produit", example = "1") @PathVariable Long id, @RequestBody TypeProduit typeProduit, WebRequest request){
        TypeProduit typeProduitUpd = typeProduitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun type de produit trouve avec l'id " + id));

        typeProduitUpd.setLibelle(typeProduit.getLibelle());

        typeProduitService.save(typeProduitUpd);

        return ResponseEntity.status(200).body("type produit modifie avec succes");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer un type de produit", description = "Supprime un type de produit par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de produit supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Type de produit non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant du type de produit", example = "1") @PathVariable Long id){
        typeProduitService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun type de produit trouve avec l'id " + id));
        typeProduitService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
