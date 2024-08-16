package com.example.onlinecourse.Dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CourseDTO {
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    private Integer slots;

    public CourseDTO(String javaBasics, double v, int i) {
    }

    public CourseDTO() {

    }
}