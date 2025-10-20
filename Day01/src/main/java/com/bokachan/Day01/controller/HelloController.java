package com.bokachan.Day01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Xin chào! Ứng dụng Spring Boot đã chạy thành công! 🎉";
    }

    @GetMapping("/boka")
    public String boka() {
        return "Bokaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa - Đã chạy thành công!";
    }

    @GetMapping("/test")
    public String test() {
        return "Trang test - Mọi thứ đều ổn!";
    }
}