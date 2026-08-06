package diti.service.impl;

import diti.entity.TypeProduit;
import diti.repository.TypeProduitRepository;
import diti.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeProduitServiceImpl implements TypeProduitService {

    @Autowired
    private TypeProduitRepository repository;

    @Override
    public TypeProduit save(TypeProduit typeProduit) {
        return repository.save(typeProduit);
    }

    @Override
    public List<TypeProduit> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TypeProduit> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
