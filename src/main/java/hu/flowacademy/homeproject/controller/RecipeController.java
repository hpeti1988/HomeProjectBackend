package hu.flowacademy.homeproject.controller;

import hu.flowacademy.homeproject.model.Recipe;
import hu.flowacademy.homeproject.model.dto.RecipeRequestDTO;
import hu.flowacademy.homeproject.model.dto.RecipeResponseDTO;
import hu.flowacademy.homeproject.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("recipes")
    public List<RecipeResponseDTO> findAllRecipe() {
        return recipeService.findAllRecipe();
    }

    @GetMapping("recipes/{id}")
    public RecipeResponseDTO findRecipeById(@PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @GetMapping("recipes/currentuser/{id}")
    public List<Recipe> findRecipeByUser(@PathVariable Long id) {
        return recipeService.findRecipeByUserId(id);
    }

    @PostMapping("users/{id}/recipes")
    public ResponseEntity<Void> createRecipe(@PathVariable Long id, @RequestBody RecipeRequestDTO recipeRequestDTO) {
       return recipeService.createRecipe(recipeRequestDTO, id);
           }

    @PutMapping("recipes/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequestDTO recipeRequestDTO) {
        return recipeService.updateRecipe(recipeRequestDTO, id);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        return recipeService.deleteRecipe(id);
    }


}
