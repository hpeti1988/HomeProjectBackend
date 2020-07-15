package hu.flowacademy.homeproject.service;

import hu.flowacademy.homeproject.model.Recipe;
import hu.flowacademy.homeproject.model.dto.RecipeRequestDTO;
import hu.flowacademy.homeproject.model.dto.RecipeResponseDTO;
import hu.flowacademy.homeproject.repository.RecipeRepository;
import hu.flowacademy.homeproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;


    @Autowired
    UserRepository userRepository;

    public List<RecipeResponseDTO> findAllRecipe() {
        if (recipeRepository.findAll().size() != 0 ) {
            List<Recipe> recipes = recipeRepository.findAll();
            List<RecipeResponseDTO> listOfRecipeResponseDTO = recipes.stream().map(recipe -> {
                RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
                recipeResponseDTO.setName(recipe.getName());
                recipeResponseDTO.setCreatedAt(recipe.getCreatedAt());
                recipeResponseDTO.setDescription(recipe.getDescription());
                recipeResponseDTO.setIngredients(recipe.getIngredients());
                recipeResponseDTO.setId(recipe.getId());
                return recipeResponseDTO;
            }).collect(Collectors.toList());
            return listOfRecipeResponseDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public RecipeResponseDTO findRecipeById (Long id) {
        if(recipeRepository.findById(id).isPresent()) {
            Recipe recipe = recipeRepository.findById(id).get();
            RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
            recipeResponseDTO.setName(recipe.getName());
            recipeResponseDTO.setCreatedAt(recipe.getCreatedAt());
            recipeResponseDTO.setDescription(recipe.getDescription());
            recipeResponseDTO.setIngredients(recipe.getIngredients());
            recipeResponseDTO.setId(recipe.getId());
            return recipeResponseDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Recipe> findRecipeByUserId (Long id) {
        return recipeRepository.findRecipesByUser(id);
    }

    public ResponseEntity<Void> createRecipe(RecipeRequestDTO recipeRequestDTO, Long id) {
            Recipe recipe = new Recipe();
            recipe.setUser(userRepository.findById(id).get());
            recipe.setName(recipeRequestDTO.getName());
            recipe.setDescription(recipeRequestDTO.getDescription());
            recipe.setCreatedAt(LocalDate.now());
            recipe.setIngredients(recipeRequestDTO.getIngredients());
            recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
            recipeRepository.save(recipe);
            return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateRecipe (RecipeRequestDTO recipeRequestDTO, Long id) {
        if(recipeRepository.findById(id).isPresent()) {
            Recipe rp = recipeRepository.findById(id).get();
            rp.setCreatedAt(LocalDate.now());
            rp.setUser(recipeRepository.findById(id).get().getUser());
            //rp.setIngredients(recipeRepository.findById(id).get().getIngredients());
            rp.setIngredients(recipeRequestDTO.getIngredients());
            rp.getIngredients().forEach(ingredient -> ingredient.setRecipe(rp));
            rp.setName(recipeRequestDTO.getName());
            rp.setDescription(recipeRequestDTO.getDescription());
            recipeRepository.save(rp);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteRecipe (Long id) {
        if(recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
