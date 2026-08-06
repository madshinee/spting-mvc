package diti.repository;

import diti.entity.TypeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeProduitRepository extends JpaRepository<TypeProduit, Long> {
    List<TypeProduit> findByLibelleContaining(String libelle);
}
