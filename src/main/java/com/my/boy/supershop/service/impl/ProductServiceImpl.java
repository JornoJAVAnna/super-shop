package com.my.boy.supershop.service.impl;

import com.my.boy.supershop.dto.ProductDto;
import com.my.boy.supershop.entity.Product;
import com.my.boy.supershop.repository.ProductRepository;
import com.my.boy.supershop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Получить все продукты
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // Получить один продукт
    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
        return toDto(product);
    }

    // Создать новый продукт
    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = new Product(dto.name(), dto.description(), dto.price());
        return toDto(productRepository.save(product));
    }

    // Обновить существующий продукт
    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());

        return toDto(productRepository.save(product));
    }

    // Удалить продукт
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Продукт не найден");
        }
        productRepository.deleteById(id);
    }

    // Вспомогательный метод: Product → ProductDto
    private ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}