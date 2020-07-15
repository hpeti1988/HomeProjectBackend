package hu.flowacademy.homeproject.controller;

import hu.flowacademy.homeproject.model.Ingredient;
import hu.flowacademy.homeproject.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public List<Ingredient> findAllIngredients () {
       return ingredientService.findAllIngredient();
    }

    @GetMapping("/ingredients/{id}")
    public Ingredient findIngredientById (@PathVariable Long id) {
        return ingredientService.findIngredientById(id);
    }


    @PostMapping("recipes/{id}/ingredients")
    public ResponseEntity<Void> createIngredient (@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<Void> updateIngredient (@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(ingredient, id);
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Void> deleteIngredient (@PathVariable Long id) {
        return ingredientService.deleteIngredient(id);
    }
}
