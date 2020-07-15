package hu.flowacademy.homeproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    private Recipe recipe;
}
