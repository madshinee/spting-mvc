package diti.service.impl;


import diti.dto.ProduitDTO;
import diti.entity.Produit;
import diti.entity.TypeProduit;
import diti.mapper.ProduitMapper;
import diti.repository.ProductRepository;
import diti.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository ;

    @Autowired
    private ProduitMapper produitMapper;

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

    @Override
    public List<Produit> findByTypeProduit(TypeProduit typeProduit) {
        return repository.findByTypeProduit(typeProduit);
    }

    @Override
    public Page<ProduitDTO> getAllProduits(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(produitMapper::toDTO);
    }
}