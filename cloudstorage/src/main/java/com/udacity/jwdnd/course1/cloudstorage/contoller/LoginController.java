package com.udacity.jwdnd.course1.cloudstorage.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    public final static String TAG_ = "LoginController";
    @GetMapping()
    public String loginView() {
        return "login";
    }
}
