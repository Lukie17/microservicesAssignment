package com.tus.orderServiceA.controller;

import org.springframework.web.bind.annotation.*;
import com.tus.orderServiceA.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestParam String username) {
        return JwtUtil.generateToken(username);
    }
}