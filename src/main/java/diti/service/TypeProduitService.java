package diti.service;

import diti.entity.TypeProduit;

import java.util.List;
import java.util.Optional;

public interface TypeProduitService {
    TypeProduit save(TypeProduit typeProduit);

    List<TypeProduit> findAll();

    Optional<TypeProduit> findById(Long id);

    void delete(Long id);
}
