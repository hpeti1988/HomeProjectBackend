package hu.flowacademy.homeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(length = 100000)
    private String description;
    @Column
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "recipe")
    @JsonManagedReference
    private List<Ingredient> ingredients;

}
