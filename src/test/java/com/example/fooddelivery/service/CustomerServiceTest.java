package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.customer.CustomerCreateDto;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.entity.Customer;
import com.example.fooddelivery.exception.CustomerNotFoundException;
import com.example.fooddelivery.mapper.CustomerMapper;
import com.example.fooddelivery.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("findCustomerById: success")
    void findCustomerById_Success() {
        Long id = 1L;
        Customer customer = new Customer();
        CustomerDto dto = new CustomerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(dto);

        CustomerDto result = customerService.findCustomerById(id);

        assertThat(result).isEqualTo(dto);
        verify(customerRepository).findById(id);
    }

    @Test
    @DisplayName("findCustomerById: not found")
    void findCustomerById_NotFound() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findCustomerById(id))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found with id: " + id);
    }

    @Test
    @DisplayName("getCustomers: success")
    void getCustomers_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(new Customer()));

        when(customerRepository.findAll(pageable)).thenReturn(page);
        when(customerMapper.toDto(any())).thenReturn(new CustomerDto());

        Page<CustomerDto> result = customerService.getCustomers(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("findByName: success")
    void findByName_Success() {
        String name = "John";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(new Customer()));

        when(customerRepository.findByFirstName(name, pageable)).thenReturn(page);
        when(customerMapper.toDto(any())).thenReturn(new CustomerDto());

        Page<CustomerDto> result = customerService.findByName(name, pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("findByLastName: success")
    void findByLastName_Success() {
        String lastName = "Doe";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(new Customer()));

        when(customerRepository.findByLastName(lastName, pageable)).thenReturn(page);
        when(customerMapper.toDto(any())).thenReturn(new CustomerDto());

        Page<CustomerDto> result = customerService.findByLastName(lastName, pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("addCustomer: success")
    void addCustomer_Success() {
        CustomerCreateDto createDto = new CustomerCreateDto();
        createDto.setFirstName("John");
        createDto.setLastName("Doe");

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerMapper.toEntity(createDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(new CustomerDto());

        CustomerDto result = customerService.addCustomer(createDto);

        assertThat(result).isNotNull();
        verify(customerRepository).save(customer);
    }

    @Test
    @DisplayName("updateCustomer: success with password update")
    void updateCustomer_Success_WithPassword() {
        Long id = 1L;
        CustomerCreateDto updateDto = new CustomerCreateDto();
        updateDto.setFirstName("New");
        updateDto.setLastName("Name");
        updateDto.setPassword("secret");

        Customer existing = new Customer();

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any())).thenReturn(existing);
        when(customerMapper.toDto(any())).thenReturn(new CustomerDto());

        customerService.updateCustomer(id, updateDto);

        assertThat(existing.getFirstName()).isEqualTo("New");
        assertThat(existing.getPassword()).isEqualTo("secret");
        verify(customerRepository).save(existing);
    }

    @Test
    @DisplayName("updateCustomer: success without password update (Empty branch)")
    void updateCustomer_Success_EmptyPassword() {
        Long id = 1L;
        CustomerCreateDto updateDto = new CustomerCreateDto();
        updateDto.setPassword("");

        Customer existing = new Customer();
        existing.setPassword("old_pass");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any())).thenReturn(existing);
        when(customerMapper.toDto(any())).thenReturn(new CustomerDto());

        customerService.updateCustomer(id, updateDto);

        assertThat(existing.getPassword()).isEqualTo("old_pass");
    }

    @Test
    @DisplayName("updateCustomer: success without password update (Null branch)")
    void updateCustomer_Success_NullPassword() {
        Long id = 1L;
        CustomerCreateDto updateDto = new CustomerCreateDto();
        updateDto.setPassword(null);

        Customer existing = new Customer();
        existing.setPassword("old_pass");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any())).thenReturn(existing);
        when(customerMapper.toDto(any())).thenReturn(new CustomerDto());

        customerService.updateCustomer(id, updateDto);

        assertThat(existing.getPassword()).isEqualTo("old_pass");
    }

    @Test
    @DisplayName("updateCustomer: not found")
    void updateCustomer_NotFound() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomer(id, new CustomerCreateDto()))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("deleteCustomerById: success")
    void deleteCustomerById_Success() {
        Long id = 1L;
        when(customerRepository.existsById(id)).thenReturn(true);

        customerService.deleteCustomerById(id);

        verify(customerRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteCustomerById: not found (Branch Coverage)")
    void deleteCustomerById_NotFound() {
        Long id = 1L;
        when(customerRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> customerService.deleteCustomerById(id))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Cant delete");

        verify(customerRepository, never()).deleteById(id);
    }
}