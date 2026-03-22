package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.entity.Customer;
import com.example.fooddelivery.exception.CustomerNotFoundException;
import com.example.fooddelivery.mapper.CustomerMapper;
import com.example.fooddelivery.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto findCustomerById(Long id) {
        log.debug("try find customer with id: {}", id);
        CustomerDto customer = customerRepository.findById(id)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        log.info("customer found: with first name: {}, last name: {} successfully",
                customer.getFirstName(), customer.getLastName());
        return customer;
    }

    public Page<CustomerDto> getCustomers(Pageable pageable) {
        log.debug("try find all customers");
        Page<CustomerDto> customers = customerRepository.findAll(pageable).map(customerMapper::toDto);
        log.info("all customers found successfully");
        return customers;
    }

    public Page<CustomerDto> findByName(String name, Pageable pageable) {
        log.debug("try find all customers by name: {}", name);
        Page<CustomerDto> customers = customerRepository.findByFirstName(name, pageable).map(customerMapper::toDto);
        log.info("all customers found by name: {} successfully", name);
        return customers;
    }

    public Page<CustomerDto> findByLastName(String lastName, Pageable pageable) {
        log.debug("try find all customers by last name: {}", lastName);
        Page<CustomerDto> customers = customerRepository
                .findByFirstName(lastName, pageable)
                .map(customerMapper::toDto);
        log.info("all customers found by last name: {} successfully", lastName);
        return customers;
    }

    @Transactional
    public CustomerDto addCustomer(CustomerCreateDto customerCreateDto) {
        log.debug("try adding customer with first name: {}, lastname: {}",
                customerCreateDto.getFirstName(), customerCreateDto.getLastName());
        Customer customer = customerMapper.toEntity(customerCreateDto);
        log.info("customer created successfully: with first name: {}, last name: {}",
                customer.getFirstName(), customer.getLastName());
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerCreateDto newCustomerDto) {
        log.debug("try updating customer with id: {}", id);
        Customer saved = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        saved.setFirstName(newCustomerDto.getFirstName());
        saved.setLastName(newCustomerDto.getLastName());
        saved.setEmail(newCustomerDto.getEmail());
        saved.setPhoneNumber(newCustomerDto.getPhoneNumber());
        if (newCustomerDto.getPassword() != null && !newCustomerDto.getPassword().isEmpty()) {
            saved.setPassword(newCustomerDto.getPassword());
        }
        Customer updated = customerRepository.save(saved);
        log.info("Customer with ID: {} updated successfully", id);
        return customerMapper.toDto(updated);
    }

    @Transactional
    public void deleteCustomerById(Long id) {
        log.debug("try deleting customer with id: {}", id);
        log.debug("check customer exist: {}", id);
        if (!customerRepository.existsById(id)) {
            log.warn("delete failed customer with id: {} not found", id);
            throw new CustomerNotFoundException("Cant delete: customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        log.info("customer deleted: with id successfully");
    }
}
