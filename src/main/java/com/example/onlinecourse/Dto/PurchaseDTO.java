package com.example.onlinecourse.Dto;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseDTO {
    private Long courseId;
    private int slotsPurchased;
    private BigDecimal totalAmount;
}