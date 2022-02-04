package com.demo.demotodo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//COMMIT MESSAGE: Step 1 - COMPLETED
@RestController
@RequestMapping(path="/api")
public class HelloController {
    @GetMapping(path = "/hello")
    public String getHelloWorld() {
        return "Hello World";
    }
}