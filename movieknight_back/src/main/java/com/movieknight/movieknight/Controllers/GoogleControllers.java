package com.movieknight.movieknight.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GoogleControllers {


    @RequestMapping("/")
    public String hello() {
       return "Welcome";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


}
