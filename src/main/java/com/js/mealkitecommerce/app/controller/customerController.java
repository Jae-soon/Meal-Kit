package com.js.mealkitecommerce.app.controller;

import com.js.mealkitecommerce.app.dto.JoinForm;
import com.js.mealkitecommerce.app.entity.Customer;
import com.js.mealkitecommerce.app.exception.EmailDuplicatedException;
import com.js.mealkitecommerce.app.global.util.Util;
import com.js.mealkitecommerce.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class customerController {
    private final CustomerService customerService;

    @GetMapping("/join")
    public String showJoin(@ModelAttribute JoinForm joinForm) {
        return "customer/join";
    }

    @PostMapping("/join")
    public String join(HttpServletRequest req, @Valid JoinForm joinForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "customer/join";
        }

        Customer oldCustomer = customerService.findCustomerByUserId(joinForm.getUserId());

        if(oldCustomer != null) {
            String msg = Util.url.encode("이미 존재하는 회원입니다..");
            return "redirect:/customer/join?errorMsg=%s".formatted(msg);
        }

        if(!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "customer/join";
        }

        try {
            customerService.join(joinForm);
        } catch (EmailDuplicatedException e) {
            bindingResult.reject("SignUpEmailDuplicated", e.getMessage());
            return "customer/join";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("SignUpFailed", e.getMessage());
            return "customer/join";
        }


        try {
            req.login(joinForm.getUsername(), joinForm.getPassword());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        String loginMsg = "로그인 되었습니다";
        return "redirect:/member/profile?msg=%s".formatted(loginMsg);
    }
}
