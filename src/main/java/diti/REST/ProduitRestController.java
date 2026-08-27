package diti.REST;


import diti.dto.ProduitDTO;
import diti.entity.Produit;
import diti.entity.TypeProduit;
import diti.exception.ResourceNotFoundException;
import diti.mapper.ProduitMapper;
import diti.service.ProductService;
import diti.service.TypeProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/produits")
@Tag(name = "Produits", description = "API de gestion des produits")
public class ProduitRestController {


    @Autowired
    private ProductService productService;

    @Autowired
    private TypeProduitService typeProduitService;

    @Autowired
    private ProduitMapper produitMapper;


    @GetMapping
    @Operation(summary = "Lister tous les produits", description = "Retourne une liste paginée de tous les produits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des produits récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public Page<ProduitDTO> getList(
            @Parameter(description = "Numéro de page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "5")
            @RequestParam(defaultValue = "5") int size){
        return productService.getAllProduits(page, size);
    }

    @GetMapping("/type/{typeId}")
    @Operation(summary = "Lister les produits par type", description = "Retourne tous les produits d'un type donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des produits du type récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Type de produit non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public List<ProduitDTO> getListByType(@Parameter(description = "Identifiant du type de produit", example = "1") @PathVariable Long typeId){
        TypeProduit typeProduit = typeProduitService.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun type de produit trouve avec l'id " + typeId));
        List<Produit> produits = productService.findByTypeProduit(typeProduit);
        return produits.stream()
                .map(produitMapper::toDTO)
                .toList();
    }

    @PostMapping
    @Operation(summary = "Créer un produit", description = "Crée un nouveau produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<?> save(@Parameter(description = "Données du produit à créer") @Valid @RequestBody ProduitDTO produitDTO, BindingResult result, WebRequest request){

        if (result.hasErrors()) {
            Map<String, String> error = Map.of("message", "Erreurs de validation");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        TypeProduit typeProduit = null;
        if (produitDTO.getTypeProduitId() != null) {
            typeProduit = typeProduitService.findById(produitDTO.getTypeProduitId()).orElse(null);
        }

        Produit produit = produitMapper.toEntity(produitDTO, typeProduit);
        Produit savedProduit = productService.save(produit);

        return ResponseEntity.status(HttpStatus.CREATED).body(produitMapper.toDTO(savedProduit));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer un produit", description = "Supprime un produit par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant du produit", example = "1") @PathVariable Long id){
        productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun produit trouve avec l'id " + id));
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par ID", description = "Retourne un produit par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit trouvé"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<ProduitDTO> getById(@Parameter(description = "Identifiant du produit", example = "1") @PathVariable Long id){
        Produit produit = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun produit trouve avec l'id " + id));
        return ResponseEntity.status(200).body(produitMapper.toDTO(produit));
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Modifier un produit", description = "Met à jour un produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit modifié avec succès"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<?> edit(@Parameter(description = "Identifiant du produit", example = "1") @PathVariable Long id, @RequestBody ProduitDTO produitDTO, WebRequest request){

        Produit produitUpd = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun produit trouve avec l'id " + id));

        TypeProduit typeProduit = null;
        if (produitDTO.getTypeProduitId() != null) {
            typeProduit = typeProduitService.findById(produitDTO.getTypeProduitId()).orElse(null);
        }

        Produit produitEntity = produitMapper.toEntity(produitDTO, typeProduit);
        produitEntity.setId(id);

        productService.save(produitEntity);

        return ResponseEntity.status(200).body("produit modifie avec succes");
    }


}
