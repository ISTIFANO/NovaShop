package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {
    @Query("select o FROM Order o where o.email=?1 and o.orderId =?2")
    public Order findByEmailAndOrderId(String email, long orderId);
}
