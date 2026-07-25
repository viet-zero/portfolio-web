package com.portfolio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String chaoMuaHe() {
        return "Chào bạn! Hệ thống Backend chạy bằng Java Spring Boot đã hoạt động thành công rồi nhé!";
    }
}