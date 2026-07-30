package diti.service;



import diti.entity.Produit;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Produit save(Produit product);

    List<Produit> findAll();

    Optional<Produit> findById(Long id);

    void delete(Long id);
}