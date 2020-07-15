package hu.flowacademy.homeproject.model.dto;

import hu.flowacademy.homeproject.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDTO {
    private Long id;

    private String name;

    private String description;

    private List<Ingredient> ingredients;
}
