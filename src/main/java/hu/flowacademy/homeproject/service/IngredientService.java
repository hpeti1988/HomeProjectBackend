package hu.flowacademy.homeproject.service;

import hu.flowacademy.homeproject.model.Ingredient;
import hu.flowacademy.homeproject.repository.IngredientRepository;
import hu.flowacademy.homeproject.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    public List<Ingredient> findAllIngredient() {
        if (recipeRepository.findAll().size() != 0) {
            return ingredientRepository.findAll();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Ingredient findIngredientById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Void> createIngredient (Ingredient ingredient) {
        if (recipeRepository.findById(ingredient.getRecipe().getId()).isPresent()) {
            ingredient.setRecipe(recipeRepository.findById(ingredient.getRecipe().getId()).get());
            ingredientRepository.save(ingredient);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> updateIngredient(Ingredient ingredient, Long id) {
        if (ingredientRepository.findById(id).isPresent()) {
            Ingredient ing = ingredientRepository.findById(id).get();
            ing.setRecipe(ingredientRepository.findById(id).get().getRecipe());
            ing.setAmount(ingredient.getAmount());
            ing.setName(ingredient.getName());
            ingredientRepository.save(ing);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteIngredient(Long id) {
        if (ingredientRepository.findById(id).isPresent()) {
            ingredientRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
