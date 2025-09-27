package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.domain.model.projection.CategoryProjection;
import com.example.novashop22.infrastructure.database.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
    Category findByCategoryId(Long categoryId);
}
