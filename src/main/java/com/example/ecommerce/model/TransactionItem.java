package com.example.ecommerce.model;

import jakarta.persistence.*;

@Entity
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    // Getters and Setters
}
