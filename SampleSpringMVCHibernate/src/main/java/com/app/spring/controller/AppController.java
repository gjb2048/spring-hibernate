package com.app.spring.controller;

import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Gareth
 */
@Controller
public class AppController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("datetime", new Date());
        return "index";
    }

    @RequestMapping(value = "/references", method = RequestMethod.GET)
    public String references(Model model) {
        return "refs";
    }
    
}
