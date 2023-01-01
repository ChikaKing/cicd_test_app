package com.test_cicd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ci-cd/api")
public class AppController {
    
    @GetMapping("test")
    public String apiTest() {
        return "CICD Api, successfully deployed and Tested.";
    }

}
