package com.example.ecommerce.service;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Ambil semua item keranjang
    public List<CartItem> getAllItems() {
        return cartRepository.findAll();
    }

    // Tambah produk ke keranjang (jika produk sudah ada → tambah quantity)
    public void addToCart(Product product) {
        List<CartItem> items = cartRepository.findAll();

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                cartRepository.save(item);
                System.out.println("Jumlah " + product.getName() + " ditambah di keranjang.");
                return;
            }
        }

        CartItem newItem = new CartItem(null, product, 1);
        cartRepository.save(newItem);
        System.out.println("Produk baru dimasukkan ke keranjang: " + product.getName());
    }

    // Hapus 1 item dari keranjang
    public void deleteCartItem(Long id) {
        if (id == null || !cartRepository.existsById(id)) {
            throw new RuntimeException("Item tidak ditemukan: ID = " + id);
        }
        cartRepository.deleteById(id);
        System.out.println("Item keranjang ID " + id + " berhasil dihapus.");
    }

    // Checkout → simpan sebagai Order + hapus semua isi keranjang
    public void checkout() {
        List<CartItem> cartItems = cartRepository.findAll();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Keranjang kamu kosong");
        }

        Order order = new Order();
        order.setUser(null); // karena belum pakai sistem login
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity());
            orderItemRepository.save(orderItem);
            System.out.println("Checkout item: " + cartItem.getProduct().getName() + " x" + cartItem.getQuantity());
        }

        cartRepository.deleteAll(cartItems);
        System.out.println("Checkout selesai, keranjang dikosongkan.");
    }
}
