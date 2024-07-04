package com.training.FirstTest.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {

    @GetMapping("hello")
    public String sayHello(){
        return "Hello World";
    }
}
