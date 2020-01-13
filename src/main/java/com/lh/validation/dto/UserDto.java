package com.lh.validation.dto;


import com.lh.validation.annotation.IdCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

/**
 * @Author liuhh    @Date 2019/12/12 9:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "姓名不能为空")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Max(value = 200,message = "年龄最大为{max}")
    @Min(value = 0,message = "年龄最小为{min}")
    private Integer age;

    @Email(message = "邮箱格式不正确")
    private String email;

    @NotNull(message = "手机号不能为空")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp ="^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;

    @NotNull
    @IdCard(message = "身份证不合法")
    @DateTimeFormat()
    private String idCard;


}
