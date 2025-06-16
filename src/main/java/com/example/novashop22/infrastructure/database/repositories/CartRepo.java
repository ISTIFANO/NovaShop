package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepo extends CrudRepository<Cart, Long>{
    @Query("select c from Cart c where c.user.email =?1 and c.cartId=?1")
    Cart findByEmailAndCartId(String email, Long cartId);

    @Query("select c from Cart c JOIN fetch c.cartItems ci JOIN FETCH ci.product p where p.productId =?1")
    List<Cart> findByProductId(Long productId);
}
