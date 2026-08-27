package diti.mapper;

import diti.dto.ProduitDTO;
import diti.entity.Produit;
import diti.entity.TypeProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProduitMapper {

    @Mapping(source = "typeProduit.id", target = "typeProduitId")
    @Mapping(source = "typeProduit.libelle", target = "typeProduitLibelle")
    ProduitDTO toDTO(Produit produit);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.libelle", target = "libelle")
    @Mapping(source = "dto.prix", target = "prix")
    @Mapping(target = "typeProduit", source = "typeProduit")
    Produit toEntity(ProduitDTO dto, TypeProduit typeProduit);
}
