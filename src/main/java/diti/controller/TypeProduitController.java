package diti.controller;

import diti.entity.TypeProduit;
import diti.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/type-produit")
public class TypeProduitController {

    @Autowired
    private TypeProduitService typeProduitService;

    @GetMapping
    public String getList(Model model){
        List<TypeProduit> types = typeProduitService.findAll();
        model.addAttribute("types", types);
        return "type-produit";
    }

    @GetMapping("/new")
    public String form(){
        return "form-type-produit";
    }

    @PostMapping
    public String save(@ModelAttribute TypeProduit typeProduit){
        typeProduitService.save(typeProduit);
        return "redirect:/type-produit";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        typeProduitService.delete(id);
        return "redirect:/type-produit";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        TypeProduit typeProduit = typeProduitService.findById(id);
        model.addAttribute("typeProduit", typeProduit);
        return "form-type-produit";
    }
}
