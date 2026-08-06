package diti.controller;


import diti.entity.Produit;
import diti.service.ProductService;
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


    @GetMapping
    public String getList(Model model){
        List<Produit>  produits =  productService.findAll();
        model.addAttribute("produits",produits);
        return "produit";
    }


    @GetMapping("/new")
    public String form(){
        return "form-product";
    }

    @PostMapping
    public String save(@ModelAttribute Produit produit){
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
        model.addAttribute("produit", produit);
        return "form-product";
    }


}

