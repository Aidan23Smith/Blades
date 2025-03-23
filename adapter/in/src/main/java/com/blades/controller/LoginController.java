package com.blades.controller;

import com.blades.converter.UserInConverter;
import com.blades.data.UserDto;
import com.blades.frontend.page.common.Page;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.port.in.LoginInService;
import com.blades.frontend.service.PageService;

import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LoginController {

    private final LoginInService loginInService;
    private final PageService pageService;
    private final UserInConverter userInConverter;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        return pageService.createPage(Page.builder("home", "home", null)
                                          .build());
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(CsrfToken token) {
        return pageService.createPage(QuestionPage.builder("login", null)
                                          .question(Input.builder().questionId("username").build())
                                          .question(Input.builder().questionId("password").type("password").build())
                                          .action("/login")
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @GetMapping("/signup")
    public ModelAndView getSignupPage(CsrfToken token) {
        return pageService.createPage(QuestionPage.builder("signup", null)
                                          .question(Input.builder().questionId("username").build())
                                          .question(Input.builder().questionId("password").type("password").build())
                                          .groupStem("signup")
                                          .action("/signup")
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping("/signup")
    public void signup(UserDto user, HttpServletResponse response) throws IOException {
        loginInService.createUser(userInConverter.toDomainUser(user));
        response.sendRedirect("/");
    }

}


