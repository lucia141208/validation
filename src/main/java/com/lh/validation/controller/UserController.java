package com.lh.validation.controller;

import com.lh.validation.annotation.DateValid;
import com.lh.validation.dto.TestDto;
import com.lh.validation.dto.UserDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @Author liuhh    @Date 2019/12/12 9:53
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping(value = "/test")
    public void test(@RequestBody  @Validated UserDto dto){
        System.out.println(dto.toString());
    }

    @PostMapping(value = "/check")
    public void checkDate(@DateValid(name = "dto",value = TestDto.class) TestDto dto){
        System.out.println(dto.toString());
    }

    @GetMapping(value = "/check")
    public void checkDate1(@DateValid(name = "dto",value = TestDto.class) TestDto dto){
        System.out.println(dto.toString());
    }
}
