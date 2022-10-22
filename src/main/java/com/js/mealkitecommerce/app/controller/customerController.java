package com.js.mealkitecommerce.app.controller;

import com.js.mealkitecommerce.app.dto.Customer.FindUsernameForm;
import com.js.mealkitecommerce.app.dto.Customer.JoinForm;
import com.js.mealkitecommerce.app.dto.Customer.ModifyForm;
import com.js.mealkitecommerce.app.dto.Customer.ModifyPasswordForm;
import com.js.mealkitecommerce.app.dto.context.CustomerContext;
import com.js.mealkitecommerce.app.entity.Customer;
import com.js.mealkitecommerce.app.exception.DataNotFoundException;
import com.js.mealkitecommerce.app.exception.EmailDuplicatedException;
import com.js.mealkitecommerce.app.exception.UserIdDuplicatedException;
import com.js.mealkitecommerce.app.global.util.Util;
import com.js.mealkitecommerce.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final PasswordEncoder passwordEncoder;

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
    public String showJoin(@ModelAttribute JoinForm joinForm) {
        return "customer/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(HttpServletRequest req, @Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/join";
        }

        if (customerService.findByUsername(joinForm.getUsername()).isPresent()) {
            throw new UserIdDuplicatedException("중복된 아이디가 존재합니다.");
        }

        if (customerService.findByEmail(joinForm.getEmail()).isPresent()) {
            throw new UserIdDuplicatedException("중복된 이메일이 존재합니다.");
        }

        if (!joinForm.getPassword().equals(joinForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "customer/join";
        }

        try {
            customerService.join(joinForm);
        } catch (EmailDuplicatedException e) {
            bindingResult.reject("SignUpEmailDuplicated", e.getMessage());
            return "customer/join";
        } catch(UserIdDuplicatedException e) {
            bindingResult.reject("SignUpUserIdDuplicated", e.getMessage());
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
    public String showModify(@ModelAttribute ModifyForm modifyForm) {
        return "customer/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal CustomerContext context, @Valid ModifyForm modifyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/modify";
        }

        if (customerService.findByUsername(modifyForm.getUsername()).isPresent()) {
            throw new UserIdDuplicatedException("중복된 아이디가 존재합니다.");
        }

        if (customerService.findByEmail(modifyForm.getEmail()).isPresent()) {
            throw new UserIdDuplicatedException("중복된 이메일이 존재합니다.");
        }


        try {
            customerService.modify(context, modifyForm);
        } catch(EmailDuplicatedException e) {
            bindingResult.reject("SignUpEmailDuplicated", e.getMessage());
            return "customer/modify";
        } catch(UserIdDuplicatedException e) {
            bindingResult.reject("SignUpUserIdDuplicated", e.getMessage());
        }

        String modifyMsg = Util.url.encode("회원정보 수정이 완료되었습니다");
        return "redirect:/customer/profile?msg=%s".formatted(modifyMsg);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String showModifyPassword(@ModelAttribute ModifyPasswordForm modifyPasswordForm) {

        return "customer/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal CustomerContext context, String password, String modifyPassword) {
        Customer loginedCustomer = customerService.findByUsername(context.getUsername()).orElseThrow(
                () -> new DataNotFoundException("Customer Not Found")
        );

        if(!passwordEncoder.matches(password, loginedCustomer.getPassword())) {
            String msg = Util.url.encode("현재 비밀번호가 틀립니다.");
            return "redirect:/member/modifyPassword?msg=%s".formatted(msg);
        }

        customerService.modifyPassword(loginedCustomer, modifyPassword);

        return "redirect:/customer/profile";
    }

    @GetMapping("/findUsername")
    public String showFindUsername(@ModelAttribute FindUsernameForm findUsernameForm) {
        return "customer/findUsername";
    }

    @PostMapping("/findUsername")
    public String findUsername(Model model, String email) {
        Customer customer = customerService.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("등록된 아이디가 존재하지 않습니다.")
        );

        model.addAttribute("customer", customer);

        return "member/findUsername";
    }
}
