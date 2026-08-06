package diti.service.impl;


import diti.entity.Produit;
import diti.repository.ProductRepository;
import diti.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository ;

    @Override
    public Produit save(Produit product) {
       return  repository.save(product);
    }

    @Override
    public List<Produit> findAll() {

        return repository.findAll();
    }

    @Override
    public Optional<Produit> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}