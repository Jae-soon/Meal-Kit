package com.js.mealkitecommerce.app.controller;

import com.js.mealkitecommerce.app.entity.Kit;
import com.js.mealkitecommerce.app.service.KitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class KitController {
    private final KitService kitService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Kit> products = kitService.findAllForPrintByOrderByIdDesc(rq.getMember());

        model.addAttribute("products", products);

        return "product/list";
    }
}
