package com.blades.frontend.service;

import com.blades.frontend.page.common.Page;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class PageService {

    public ModelAndView createPage(Page page) {
        return new ModelAndView(page.getTemplateName(), "page", page);
    }

}
