package com.springai.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    @GetMapping("/askview")
    public String askView() {
        return "ask";
    }

}
