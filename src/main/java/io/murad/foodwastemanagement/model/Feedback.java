package io.murad.foodwastemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String message;

    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Food food;
    // Constructors, getters, and setters
}
