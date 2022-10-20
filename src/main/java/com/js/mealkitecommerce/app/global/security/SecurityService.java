package com.js.mealkitecommerce.app.global.security;

import com.js.mealkitecommerce.app.dto.context.CustomerContext;
import com.js.mealkitecommerce.app.entity.Customer;
import com.js.mealkitecommerce.app.exception.UserIdNotFountException;
import com.js.mealkitecommerce.app.global.UserRole;
import com.js.mealkitecommerce.app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new UserIdNotFountException("존재하는 회원이 없습니다.")
        );

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new CustomerContext(customer, authorities);
    }
}
