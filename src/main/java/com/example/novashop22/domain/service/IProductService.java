package com.example.novashop22.domain.service;

import com.example.novashop22.domain.model.ProductDTO;
import com.example.novashop22.domain.model.ProductResponse;
import com.example.novashop22.infrastructure.database.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {

    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
                                     String sortOrder);

    ProductDTO updateProduct(Long productId, Product product);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
                                           String sortOrder);

    String deleteProduct(Long productId);

}