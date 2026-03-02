package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstName(String name);

    List<Customer> findByLastName(String lastName);
}
