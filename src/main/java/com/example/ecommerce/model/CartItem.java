package com.example.ecommerce.model;

import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    // ===== Constructors =====

    public CartItem() {}

    public CartItem(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    // Constructor tanpa user (untuk sistem tanpa login)
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ===== Utility for debugging/log =====

    @Override
    public String toString() {
        return "CartItem{" +
               "id=" + id +
               ", product=" + (product != null ? product.getName() : "null") +
               ", quantity=" + quantity +
               '}';
    }
}
