package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Customer;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByFirstName(String name, Pageable pageable);

    Page<Customer> findByLastName(String lastName, Pageable pageable);

    @Override
    @NullMarked
    Page<Customer> findAll(Pageable pageable);
}
