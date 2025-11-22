package com.example.novashop22.infrastructure.http.controller.internal;


import com.example.novashop22.commun.payload.AppConstants;
import com.example.novashop22.domain.model.CategorieDto;
import com.example.novashop22.domain.model.CategoryResponse;
import com.example.novashop22.domain.service.ICategorieService;
import com.example.novashop22.infrastructure.database.entities.Category;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api")
@Tag(name = "Categories", description = "Endpoints for category management and browsing")
public class CategoryController {

    @Autowired
    private ICategorieService categoryService;

    @Operation(summary = "Create category", description = "Create a new category (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/admin/category")
    public ResponseEntity<CategorieDto> createCategory(@Valid @RequestBody Category category) {
        CategorieDto savedCategorieDto = categoryService.createCategorie(category);

        return new ResponseEntity<CategorieDto>(savedCategorieDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get categories", description = "Retrieve paginated categories")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Categories found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.FOUND);
    }

    @Operation(summary = "Update category", description = "Update an existing category (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategorieDto> updateCategory(@RequestBody Category category,
                                                      @PathVariable Long categoryId) {
        CategorieDto categoryDTO = categoryService.updateCategorie(categoryId,category);

        return new ResponseEntity<CategorieDto>(categoryDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete category", description = "Delete a category by id (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryService.deleteCategorie(categoryId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}