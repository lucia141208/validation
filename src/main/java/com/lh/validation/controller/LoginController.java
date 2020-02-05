package com.lh.validation.controller;

import com.lh.validation.annotation.UserLoginToken;
import com.lh.validation.dto.LoginUser;
import com.lh.validation.result.ResultResponse;
import com.lh.validation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultResponse login(@RequestBody LoginUser loginUser){
        return userService.login(loginUser);
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

}
