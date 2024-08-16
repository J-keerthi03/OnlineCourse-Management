package com.example.onlinecourse.controller;

import com.example.onlinecourse.Dto.PurchaseDTO;
import com.example.onlinecourse.model.Purchase;
import com.example.onlinecourse.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Purchase> purchaseProduct(@RequestBody PurchaseDTO purchaseDTO) {
        return ResponseEntity.ok(purchaseService.purchaseProduct(purchaseDTO));
    }
}