package diti.controller;


import diti.entity.Produit;
import diti.entity.TypeProduit;
import diti.service.ProductService;
import diti.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produit")
public class ProduitController {


    @Autowired
    private ProductService productService;

    @Autowired
    private TypeProduitService typeProduitService;


    @GetMapping
    public String getList(Model model){
        List<Produit>  produits =  productService.findAll();
        model.addAttribute("produits",produits);
        return "produit";
    }

    @GetMapping("/type/{typeId}")
    public String getListByType(@PathVariable Long typeId, Model model){
        TypeProduit typeProduit = typeProduitService.findById(typeId).get();
        List<Produit> produits = productService.findByTypeProduit(typeProduit);
        model.addAttribute("produits", produits);
        model.addAttribute("selectedType", typeProduit);
        return "produit";
    }


    @GetMapping("/new")
    public String form(Model model){
        List<TypeProduit> types = typeProduitService.findAll();
        model.addAttribute("types", types);
        return "form-product";
    }

    @PostMapping
    public String save(@ModelAttribute Produit produit, @RequestParam(required = false) Long typeProduitId){
        if(typeProduitId != null) {
            TypeProduit typeProduit = typeProduitService.findById(typeProduitId).get();
            produit.setTypeProduit(typeProduit);
        }
        productService.save(produit);
        return "redirect:/produit";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/produit";
    }



    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Produit produit =  productService.findById(id).get();
        List<TypeProduit> types = typeProduitService.findAll();
        model.addAttribute("produit", produit);
        model.addAttribute("types", types);
        return "form-product";
    }


}

