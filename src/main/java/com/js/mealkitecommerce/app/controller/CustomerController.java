package com.js.mealkitecommerce.app.controller;

import com.js.mealkitecommerce.app.model.VO.Customer.*;
import com.js.mealkitecommerce.app.model.context.CustomerContext;
import com.js.mealkitecommerce.app.entity.Customer;
import com.js.mealkitecommerce.app.exception.DataNotFoundException;
import com.js.mealkitecommerce.app.exception.EmailDuplicatedException;
import com.js.mealkitecommerce.app.exception.NotMatchPresentPassword;
import com.js.mealkitecommerce.app.exception.UserIdDuplicatedException;
import com.js.mealkitecommerce.app.global.email.service.EmailService;
import com.js.mealkitecommerce.app.global.util.Util;
import com.js.mealkitecommerce.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    // model과 attributeModel의 차이점 : 데이터를 불러오고 싶을 경우 modelattribute 어노테이션 사용
    // 데이터를 전송할 때에는 웬만하면 model 을 사용하는 것이 맞음
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal CustomerContext context, Model model) {
        Customer customer = customerService.findByUsername(context.getUsername()).orElseThrow(
                () -> new DataNotFoundException("Customer Not Found")
        );

        model.addAttribute("customer", customer);

        return "customer/profile";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin(@ModelAttribute("joinForm") JoinRequestVO joinForm) {
        return "customer/join";
    }

    @GetMapping("/checkEmail")
    @ResponseBody
    public Customer checkDuplicateEmail(SingleParamVO param) {
        Customer customer = customerService.findByEmail(param.getParam()).orElse(null);

        return customer;
    }

    @GetMapping("/checkUsername")
    @ResponseBody
    public Customer checkDuplicateUsername(SingleParamVO param) {
        Customer customer = customerService.findByUsername(param.getParam()).orElse(null);

        return customer;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(HttpServletRequest req, @Valid JoinRequestVO joinForm, BindingResult bindingResult) {
        try {
            customerService.join(joinForm);
        } catch (EmailDuplicatedException e) {
            bindingResult.reject("EmailDuplicatedException", e.getMessage());
            return "customer/join";
        } catch(UserIdDuplicatedException e) {
            bindingResult.reject("UserIdDuplicatedException", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("SignUpFailed", e.getMessage());
            return "customer/join";
        }

        try {
            req.login(joinForm.getUsername(), joinForm.getPassword());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        String loginMsg = Util.url.encode("로그인 되었습니다");
        return "redirect:/customer/profile?msg=%s".formatted(loginMsg);
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "customer/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String showModify(@AuthenticationPrincipal CustomerContext context, Model model) {
        Customer customer = customerService.findByUsername(context.getUsername()).orElseThrow(
                () -> new DataNotFoundException("Customer Not Found")
        );

        model.addAttribute("c", customer);

        return "customer/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal CustomerContext context, @Valid Customer modifyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/modify";
        }

        try {
            customerService.modify(context, modifyForm);
        } catch(EmailDuplicatedException e) {
            bindingResult.reject("EmailDuplicatedException", e.getMessage());
            return "customer/modify";
        } catch(UserIdDuplicatedException e) {
            bindingResult.reject("UserIdDuplicatedException", e.getMessage());
        }

        return "redirect:/customer/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String showModifyPassword(@ModelAttribute ModifyPasswordForm modifyPasswordForm) {

        return "customer/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal CustomerContext context, @Valid ModifyPasswordForm modifyPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/modifyPassword";
        }

        Customer loginedCustomer = customerService.findByUsername(context.getUsername()).orElseThrow(
                () -> new DataNotFoundException("Customer Not Found")
        );

        if(!passwordEncoder.matches(modifyPasswordForm.getPassword(), loginedCustomer.getPassword())) {
            throw new NotMatchPresentPassword("현재 비밀번호가 틀립니다.");
        }

        try {
            customerService.modifyPassword(loginedCustomer, modifyPasswordForm.getModifyPassword());
        } catch (DataNotFoundException e) {
            bindingResult.reject("EmailDuplicatedException", e.getMessage());
            return "customer/modifyPassword";
        } catch (NotMatchPresentPassword e) {
            bindingResult.reject("NotMatchPresentPassword", e.getMessage());
            return "customer/modifyPassword";
        }

        return "redirect:/customer/profile";
    }

    @GetMapping("/findUsername")
    public String showFindUsername(@ModelAttribute FindUsernameForm findUsernameForm) {
        return "customer/findUsername";
    }

    @PostMapping("/findUsername")
    public String findUsername(Model model, @Valid FindUsernameForm findUsernameForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/findUsername";
        }

        Customer customer = customerService.findByEmail(findUsernameForm.getEmail()).orElseThrow(
                () -> new DataNotFoundException("등록된 아이디가 존재하지 않습니다.")
        );

        model.addAttribute("customer", customer);

        return "customer/findUsername";
    }

    @GetMapping("/findPassword")
    public String showFindPassword(@ModelAttribute FindPasswordForm findPasswordForm) {
        return "customer/findPassword";
    }

    @PostMapping("/findPassword")
    public String findPassword(@Valid FindPasswordForm findPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/findPassword";
        }

        Customer customer = customerService.findByUsernameAndEmail(findPasswordForm.getUsername(), findPasswordForm.getEmail()).orElseThrow(
                () -> new DataNotFoundException("등록된 사용자가 없습니다.")
        );

        String newPassword = RandomStringUtils.randomAlphanumeric(10);;

        try {
            customerService.setNewPassword(customer, newPassword);
        } catch (DataNotFoundException e) {
            bindingResult.reject("등록된 사용자가 없습니다.", e.getMessage());
        }

        emailService.sendSimpleMessage(customer, newPassword);

        return "redirect:/customer/login";
    }
}
