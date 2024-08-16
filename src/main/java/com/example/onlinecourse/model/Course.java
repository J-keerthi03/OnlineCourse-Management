package com.example.onlinecourse.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer slots;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Survey> surveys;


    public Course(String name, BigDecimal price, int slots) {
        this.name = name;
        this.price = price;
        this.slots = slots;
    }

    public Course() {
    }
}
