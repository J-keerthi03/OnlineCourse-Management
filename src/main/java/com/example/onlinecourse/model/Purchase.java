package com.example.onlinecourse.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne
    private Course course;
    private int slotsPurchased;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
}
