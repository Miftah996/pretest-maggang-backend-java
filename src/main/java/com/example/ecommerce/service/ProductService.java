package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        logger.info("Mengambil semua produk...");
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        logger.info("Mencari produk dengan ID: {}", id);
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        if (product == null) {
            logger.warn("Produk null saat mencoba menyimpan.");
            throw new IllegalArgumentException("Produk tidak boleh null");
        }
        logger.info("Menyimpan produk: {}", product.getName());
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        logger.info("Menghapus produk dengan ID: {}", id);
        productRepository.deleteById(id);
    }
}
