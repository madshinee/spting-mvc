package diti.REST;

import diti.entity.TypeProduit;
import diti.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type-produits")
public class TypeProduitRestController {

    @Autowired
    private TypeProduitService typeProduitService;

    @GetMapping
    public List<TypeProduit> getList(){
        return typeProduitService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeProduit> getById(@PathVariable Long id){
        Optional<TypeProduit> typeProduit = typeProduitService.findById(id);
        if (!typeProduit.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(200).body(typeProduit.get());
    }

    @PostMapping
    public ResponseEntity<TypeProduit> save(@RequestBody TypeProduit typeProduit){
        return ResponseEntity.status(HttpStatus.CREATED).body(typeProduitService.save(typeProduit));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edit(@PathVariable Long id, @RequestBody TypeProduit typeProduit){
        Optional<TypeProduit> typeProduitUpd = typeProduitService.findById(id);
        if (!typeProduitUpd.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        typeProduitUpd.get().setLibelle(typeProduit.getLibelle());

        typeProduitService.save(typeProduitUpd.get());

        return ResponseEntity.status(200).body("type produit modifie avec succes");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<TypeProduit> typeProduit = typeProduitService.findById(id);
        if (!typeProduit.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        typeProduitService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
