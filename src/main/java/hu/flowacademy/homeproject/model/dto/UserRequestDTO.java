package hu.flowacademy.homeproject.model.dto;

import hu.flowacademy.homeproject.model.Recipe;
import hu.flowacademy.homeproject.model.Role;
import hu.flowacademy.homeproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate dateOfBirth;

    private Role role;

    private List<Recipe> recipes;

    public User toEntity() {
        return User.builder().id(id).username(username).password(password).build();
    }
}
