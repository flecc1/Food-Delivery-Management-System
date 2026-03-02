package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id:\\d+}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    public CustomerDto addCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.addCustomer(customerCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.updateCustomer(id, customerCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
