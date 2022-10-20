package com.js.mealkitecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Customer extends BaseEntity {
    @Size(min = 5, max = 25, message = "사용자 ID는 5자 이상 25자 이하로 적어주세요.")
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String email;
    private String address;
    private String tel;
}
