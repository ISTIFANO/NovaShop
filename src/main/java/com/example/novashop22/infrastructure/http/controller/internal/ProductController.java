package com.example.novashop22.infrastructure.http.controller.internal;

import java.io.IOException;

import com.example.novashop22.commun.payload.AppConstants;
import com.example.novashop22.domain.model.ProductDTO;
import com.example.novashop22.domain.model.ProductResponse;
import com.example.novashop22.domain.service.IProductService;
import com.example.novashop22.infrastructure.database.entities.Product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Products", description = "Endpoints for product management and browsing")
@RestController
@RequestMapping("/api")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // Add Product
    @Operation(summary = "Add a new product", description = "Create a new product under the specified category (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @Valid @RequestBody Product product,
            @PathVariable Long categoryId) {

        ProductDTO savedProduct = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Get all products
    @Operation(summary = "Get all products", description = "Returns a paginated list of products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String sortOrder) {

        ProductResponse productResponse =
                productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    // Get products by category
    @Operation(summary = "Get products by category", description = "Returns products for a given category (paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String sortOrder) {

        ProductResponse productResponse =
                productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    // Search by keyword
    @Operation(summary = "Search products by keyword", description = "Search products containing the provided keyword")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved"),
    })
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String sortOrder) {

        ProductResponse productResponse =
                productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    // Update product info
    @Operation(summary = "Update product", description = "Update product basic information (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Valid @RequestBody Product product,
            @PathVariable Long productId) {

        ProductDTO updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Update product image
    @Operation(summary = "Update product image", description = "Update the image for a product (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product image updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(
            @PathVariable Long productId,
            @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete product
    @Operation(summary = "Delete product", description = "Delete a product by id (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<String> deleteProductByCategory(@PathVariable Long productId) {
        String status = productService.deleteProduct(productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
