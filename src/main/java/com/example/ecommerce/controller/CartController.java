package com.example.ecommerce.controller;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    // ✅ Tambah produk ke keranjang
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Long> body) {
        Long productId = body.get("productId");

        if (productId == null) {
            return ResponseEntity.badRequest().body("Product ID is missing");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        cartService.addToCart(product);
        return ResponseEntity.ok("Produk berhasil ditambahkan ke keranjang!");
    }

    // ✅ Ambil isi keranjang
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart() {
        return ResponseEntity.ok(cartService.getAllItems());
    }

    // ✅ Hapus item dari keranjang
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        try {
            cartService.deleteCartItem(id);
            return ResponseEntity.ok("Item berhasil dihapus dari keranjang.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal menghapus item dengan id " + id + ": " + e.getMessage());
        }
    }

    // ✅ Ubah jumlah item di keranjang
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        int newQty = body.getOrDefault("quantity", -1);
        if (newQty < 1) {
            return ResponseEntity.badRequest().body("Jumlah minimal adalah 1.");
        }

        CartItem item = cartRepository.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        item.setQuantity(newQty);
        cartRepository.save(item);
        return ResponseEntity.ok("Jumlah item berhasil diperbarui.");
    }

    // ✅ Checkout keranjang
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout() {
        try {
            cartService.checkout();
            return ResponseEntity.ok("Checkout berhasil!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Checkout gagal: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Terjadi error saat checkout: " + e.getMessage());
        }
    }
}
