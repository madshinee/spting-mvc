package diti.repository.impl;

import diti.entity.TypeProduit;
import diti.repository.TypeProduitRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TypeProduitRepositoryImpl implements TypeProduitRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(TypeProduit typeProduit) {
        entityManager.merge(typeProduit);
    }

    @Override
    public List<TypeProduit> findAll() {
        return entityManager
                .createQuery("FROM TypeProduit", TypeProduit.class)
                .getResultList();
    }

    @Override
    public TypeProduit findById(Long id) {
        return entityManager.find(TypeProduit.class, id);
    }

    @Override
    public void delete(Long id) {
        TypeProduit typeProduit = findById(id);

        if(typeProduit != null) {
            entityManager.remove(typeProduit);
        }
    }
}
