package com.lh.validation.service;

import com.lh.validation.dto.LoginUser;
import com.lh.validation.result.ResultResponse;

public interface UserService {
    ResultResponse login(LoginUser loginUser);

    LoginUser findUserById(String userId);
}
