package com.js.mealkitecommerce.app.model.VO.Customer;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class FindUsernameForm {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;
}
