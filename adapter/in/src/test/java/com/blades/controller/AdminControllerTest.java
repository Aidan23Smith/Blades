package com.blades.controller;

import com.blades.frontend.page.common.Page;
import com.blades.frontend.service.PageService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private PageService pageService;
    @InjectMocks
    private AdminController underTest;

    @Mock
    private ModelAndView modelAndView;

    @Test
    void admin() {
        Page expected = Page.builder("admin", "admin", null).build();
        when(pageService.createPage(expected)).thenReturn(modelAndView);

        assertEquals(underTest.admin(), modelAndView);
    }

}
