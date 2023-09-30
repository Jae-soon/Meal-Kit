package com.js.mealkitecommerce.app.service;

import com.js.mealkitecommerce.app.model.VO.Customer.JoinForm;
import com.js.mealkitecommerce.app.model.VO.Customer.ModifyCustomerVO;
import com.js.mealkitecommerce.app.model.context.CustomerContext;
import com.js.mealkitecommerce.app.entity.Customer;
import com.js.mealkitecommerce.app.exception.DataNotFoundException;
import com.js.mealkitecommerce.app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer join(JoinForm joinForm) {
        Customer customer = Customer.builder()
                .name(joinForm.getName())
                .username(joinForm.getUsername())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .email(joinForm.getEmail())
                .address(joinForm.getAddress())
                .tel(joinForm.getTel())
                .build();

        customerRepository.save(customer);

        return customer;
    }

    public void modify(CustomerContext context, Customer modifyForm) {
        Customer customer = customerRepository.findByUsername(context.getUsername()).orElseThrow(
                () -> new DataNotFoundException("Customer Not Found"));

        customer.setName(modifyForm.getName());
        customer.setEmail(modifyForm.getEmail());
        customer.setAddress(modifyForm.getAddress());
        customer.setTel(modifyForm.getTel());

        customerRepository.save(customer);
    }

    public void modifyPassword(Customer customer, String modifyPassword) {
        String encodePassword = passwordEncoder.encode(modifyPassword);
        customer.setPassword(encodePassword);

        customerRepository.save(customer);
    }

    public Optional<Customer> findByUsernameAndEmail(String username, String email) {
        return customerRepository.findByUsernameAndEmail(username, email);
    }

    public void setNewPassword(Customer customer, String newPassword) {
        String encodePassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodePassword);

        customerRepository.save(customer);
    }
}
