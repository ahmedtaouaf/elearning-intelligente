package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Auth {
    @GetMapping("/auth/2-delete-account")
    public String deleteAccount2() {
        return "auth/2-delete-account";
    }

    @GetMapping("/auth/2-lock-screen")
    public String lockScreen2() {
        return "auth/2-lock-screen";
    }

    @GetMapping("/auth/2-login-pin")
    public String loginPin2() {
        return "auth/2-login-pin";
    }

    @GetMapping("/auth/2-new-pass")
    public String newPass2() {
        return "auth/2-new-pass";
    }

    @GetMapping("/auth/2-reset-pass")
    public String resetPass2() {
        return "auth/2-reset-pass";
    }

    @GetMapping("/auth/2-sign-in")
    public String signIn2() {
        return "auth/2-sign-in";
    }

    @GetMapping("/auth/2-sign-up")
    public String signUp2() {
        return "auth/2-sign-up";
    }

    @GetMapping("/auth/2-success-mail")
    public String successMail2() {
        return "auth/2-success-mail";
    }

    @GetMapping("/auth/2-two-factor")
    public String twoFactor2() {
        return "auth/2-two-factor";
    }

    @GetMapping("/auth/delete-account")
    public String deleteAccount() {
        return "auth/delete-account";
    }
    
    @GetMapping("/auth/lock-screen")
    public String lockScreen() {
        return "auth/lock-screen";
    }

    @GetMapping("/auth/login-pin")
    public String loginPin() {
        return "auth/login-pin";
    }

    @GetMapping("/auth/new-pass")
    public String newPass() {
        return "auth/new-pass";
    }

    @GetMapping("/auth/reset-pass")
    public String resetPass() {
        return "auth/reset-pass";
    }

    @GetMapping("/auth/sign-in")
    public String signIn() {
        return "auth/sign-in";
    }

    @GetMapping("/auth/sign-up")
    public String signUp() {
        return "auth/sign-up";
    }

    @GetMapping("/auth/success-mail")
    public String successMail() {
        return "auth/success-mail";
    }

    @GetMapping("/auth/two-factor")
    public String twoFactor() {
        return "auth/two-factor";
    }    
}