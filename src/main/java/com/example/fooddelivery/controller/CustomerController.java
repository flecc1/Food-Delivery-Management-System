package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<CustomerDto> getAllCustomers(@RequestParam(value = "firstName") String firstName,
                                             @RequestParam(value = "lastName") String lastName) {
        if (firstName != null) {
            return customerService.findByName(firstName);
        } if(lastName != null) {
            return customerService.findByLastName(lastName);
        }
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
