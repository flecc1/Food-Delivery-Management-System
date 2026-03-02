package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

    public Customer toEntity(CustomerCreateDto customerCreateDto) {
        if (customerCreateDto == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(customerCreateDto.getFirstName());
        customer.setLastName(customerCreateDto.getLastName());
        customer.setEmail(customerCreateDto.getEmail());
        customer.setPhoneNumber(customerCreateDto.getPhoneNumber());
        customer.setPassword(customerCreateDto.getPassword());
        return customer;
    }
}
