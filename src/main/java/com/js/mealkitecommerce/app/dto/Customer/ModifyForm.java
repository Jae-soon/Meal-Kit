package com.js.mealkitecommerce.app.dto.Customer;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ModifyForm {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String tel;
}
