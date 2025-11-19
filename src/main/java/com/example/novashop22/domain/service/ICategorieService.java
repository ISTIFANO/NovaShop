package com.example.novashop22.domain.service;

import com.example.novashop22.domain.model.CategorieDto;
import com.example.novashop22.domain.model.CategoryResponse;
import com.example.novashop22.infrastructure.database.entities.Category;

public interface ICategorieService {

    CategorieDto getCategoryById(Long id);
    CategorieDto getCategorieByName(String name);
    CategorieDto createCategorie(Category categorieDto);
    CategorieDto updateCategorie(Long id, Category categorie);
    String deleteCategorie(Long id);
    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

}
