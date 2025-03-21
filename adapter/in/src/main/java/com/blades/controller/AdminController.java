package com.blades.controller;

import com.blades.frontend.page.common.Page;
import com.blades.frontend.service.PageService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AdminController {

    private final PageService pageService;

    @GetMapping("/admin")
    public ModelAndView admin() {
        return pageService.createPage(Page.builder("admin", "admin").build());
    }

}
