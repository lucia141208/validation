package com.lh.validation.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lh.validation.dto.LoginUser;
import com.lh.validation.result.ResultResponse;
import com.lh.validation.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public ResultResponse login(LoginUser loginUser) {
        if (!loginUser.getPassword().equals("123123") ){
            return ResultResponse.builder().code(500).msg("密码错误").build();
        }
        if (!loginUser.getUsername().equals("admin") ){
            return ResultResponse.builder().code(500).msg("用户不存在").build();
        }
        loginUser.setId("1");
        loginUser.setToken(getToken(loginUser));
        return ResultResponse.builder().code(200).msg("success").data(loginUser).build();
    }

    @Override
    public LoginUser findUserById(String userId) {
        LoginUser loginUser = LoginUser.builder().Id("1").password("123123").username("admin").build();
        return loginUser;
    }

    public String getToken(LoginUser user) {
        String token="";
        token= JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
