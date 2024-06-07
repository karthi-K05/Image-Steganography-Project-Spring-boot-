package com.example.SteganographyProject.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {

    @GetMapping()
    public String IndexPage(){
        return "index";
    }

    @GetMapping("/encryption")
    public String encryption(){
        return "encryption";
    }

    @GetMapping("/decryption")
    public String decryption(){
        return "decryption";
    }

//    @GetMapping("/error")
//    public String error(Model model, HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
//        map.put("message", request.getAttribute("javax.servlet.error.message"));
//        model.addAllAttributes(map);
//        return "error";
//    }
}
