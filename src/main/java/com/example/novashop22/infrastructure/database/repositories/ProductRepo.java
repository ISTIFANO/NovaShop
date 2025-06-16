package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {
    Page<Product> findByProductNameLike(String keyword, Pageable pageDetails);

}
