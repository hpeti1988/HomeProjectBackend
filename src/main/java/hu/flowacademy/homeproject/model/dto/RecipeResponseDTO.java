package hu.flowacademy.homeproject.model.dto;

import hu.flowacademy.homeproject.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDTO {
    private Long id;

    private String name;

    private String description;

    private LocalDate createdAt;

    private List<Ingredient> ingredients;

}
