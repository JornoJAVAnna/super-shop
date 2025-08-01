package com.my.boy.supershop.service;

import com.my.boy.supershop.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto createProduct(ProductDto dto);

    ProductDto updateProduct(Long id, ProductDto dto);

    void deleteProduct(Long id);
}
