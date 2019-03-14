package com.dubboclub.dk.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: dubbokeeper
 * @description: ${description}
 * @author: benyamin
 * @create: 2019-03-13 16:19
 **/
@Controller
public class LoginController {

    @RequestMapping("/login.htm")
    public String loginPage() {
        return "login";
    }
}
