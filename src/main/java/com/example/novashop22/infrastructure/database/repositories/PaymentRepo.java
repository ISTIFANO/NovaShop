package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends CrudRepository<Payment, Long> {
}
