package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.CartItem;
import com.example.novashop22.infrastructure.database.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends CrudRepository<CartItem, Long> {
    @Query("SELECT ci.product FROM CartItem ci WHERE ci.product.productId = ?1")
    Product findProductById(Long productId);
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}
