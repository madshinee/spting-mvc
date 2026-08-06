package diti.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_produit")
public class TypeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeProduit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produit> produits;

    public TypeProduit() {
    }

    public TypeProduit(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "TypeProduit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
