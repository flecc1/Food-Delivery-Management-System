package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Клиенты", description = "Управление данными покупателей")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id:\\d+}")
    @Operation(summary = "Найти клиента по ID")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        log.info("request for getCustomerById {}", id);
        return customerService.findCustomerById(id);
    }

    @GetMapping
    @Operation(summary = "Список всех клиентов", description = "Поиск по имени или фамилии")
    public Page<CustomerDto> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {
        log.info("request for getAllCustomers with first name {}, last name: {}", firstName, lastName);
        Pageable pageable = PageRequest.of(page, size);
        if (firstName != null) {
            return customerService.findByName(firstName, pageable);
        }
        if (lastName != null) {
            return customerService.findByLastName(lastName, pageable);
        }
        return customerService.getCustomers(pageable);
    }

    @PostMapping
    @Operation(summary = "Зарегистрировать нового клиента")
    public CustomerDto addCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        log.info("request for addCustomer with name: {}, and last name: {}",
                customerCreateDto.getFirstName(),  customerCreateDto.getLastName());
        return customerService.addCustomer(customerCreateDto);
    }

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Изменить данные клиента")
    public CustomerDto updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerCreateDto customerCreateDto) {
        log.info("request for updateCustomer with id: {}, name: {}", id, customerCreateDto.getFirstName());
        return customerService.updateCustomer(id, customerCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(summary = "Удалить аккаунт клиента")
    public void deleteCustomer(@PathVariable Long id) {
        log.info("request for deleteCustomer with id: {}", id);
        customerService.deleteCustomerById(id);
    }
}
