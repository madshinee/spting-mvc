package diti.repository;


import diti.entity.Produit;
import diti.entity.TypeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Produit, Long > {

        List<Produit> findByLibelle(String libelle);

        List<Produit> findByLibelleContainingAndPrixGreaterThan(String libelle, double prix);

    List<Produit> findByTypeProduit(TypeProduit typeProduit);
}
