package com.js.mealkitecommerce.app.model.context;

import com.js.mealkitecommerce.app.entity.Customer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CustomerContext extends User {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String name;
    private final String email;
    private final String address;
    private final String tel;

    public CustomerContext(Customer customer, List<GrantedAuthority> authorities) {
        super(customer.getUsername(), customer.getPassword(), authorities);
        this.id = customer.getId();
        this.createDate = customer.getCreateDate();
        this.modifyDate = customer.getModifyDate();
        this.username = customer.getUsername();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.tel = customer.getTel();
    }

    public Customer getCustomer() {
        return Customer
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .name(name)
                .email(email)
                .address(address)
                .tel(tel)
                .build();
    }
}
