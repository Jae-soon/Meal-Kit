package com.js.mealkitecommerce.app.service;

import com.js.mealkitecommerce.app.dto.JoinForm;
import com.js.mealkitecommerce.app.entity.Customer;
import com.js.mealkitecommerce.app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    public Customer findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username).orElse(null);
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
}
