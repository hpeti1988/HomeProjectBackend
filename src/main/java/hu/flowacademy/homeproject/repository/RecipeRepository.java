package hu.flowacademy.homeproject.repository;

import hu.flowacademy.homeproject.model.Recipe;
import hu.flowacademy.homeproject.model.dto.RecipeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository <Recipe,Long> {

    @Query("SELECT r FROM Recipe r where r.user.id = ?1")
    List<Recipe> findRecipesByUser(Long id);
}
