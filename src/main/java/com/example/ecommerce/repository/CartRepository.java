package com.example.ecommerce.repository;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

     // Jika kamu mulai pakai login user
    // List<CartItem> findByUser(User user);

    // (Opsional) Bisa tambahkan custom query jika mau ambil item per produk, dll
}
