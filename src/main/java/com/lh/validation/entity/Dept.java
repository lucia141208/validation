package com.lh.validation.entity;

import com.lh.validation.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by liguowang on 2020/1/7.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    @ExcelColumn(title = "用户ID", index = 1, dataType = Integer.class)
    private Integer id;
    @ExcelColumn(title = "登录名", index = 2, dataType = String.class)
    private String username;
    @ExcelColumn(title = "真实姓名", index = 3, dataType = String.class)
    private String nickname;
    @ExcelColumn(title = "test",  dataType = Date.class)
    private Date startDate;

    private Date endDate;


}
