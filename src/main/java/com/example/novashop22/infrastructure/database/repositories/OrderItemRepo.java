package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends CrudRepository<OrderItem, Long> {
}
