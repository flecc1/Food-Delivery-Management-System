package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.entity.Customer;
import com.example.fooddelivery.exception.CustomerNotFoundException;
import com.example.fooddelivery.mapper.CustomerMapper;
import com.example.fooddelivery.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto findCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toDto)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public List<CustomerDto> findByName(String name) {
        return customerRepository.findByFirstName(name)
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public List<CustomerDto> findByLastName(String lastName) {
        return customerRepository.findByLastName(lastName)
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Transactional
    public CustomerDto addCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer = customerMapper.toEntity(customerCreateDto);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerCreateDto newCustomerDto) {
        Customer saved = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        saved.setFirstName(newCustomerDto.getFirstName());
        saved.setLastName(newCustomerDto.getLastName());
        saved.setEmail(newCustomerDto.getEmail());
        saved.setPhoneNumber(newCustomerDto.getPhoneNumber());
        if (newCustomerDto.getPassword() != null && !newCustomerDto.getPassword().isEmpty()) {
            saved.setPassword(newCustomerDto.getPassword());
        }
        return customerMapper.toDto(customerRepository.save(saved));
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Cant delete: customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
