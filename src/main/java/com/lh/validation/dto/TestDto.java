package com.lh.validation.dto;

import com.lh.validation.annotation.DateRange;
import com.lh.validation.annotation.DateValid;
import lombok.Data;

/**
 * @Author liuhh    @Date 2020/1/10 11:00
 */
@Data
public class TestDto {

    @DateRange(isStart = true,startField = "beginTime",endField = "endTime",format = "yyyy-MM-dd HH:ss:mm",message = "beginTime not ng endTime!!!")
    private String beginTime;

    @DateRange(isStart = false,startField = "beginTime",endField = "endTime",format = "yyyy-MM-dd HH:ss:mm",message = "beginTime not ng endTime!!!")
    private String endTime;

    @DateRange(isStart = true,startField = "begin",endField = "end",format = "yyyy-MM-dd HH:ss:mm")
    private String begin;

    @DateRange(isStart = false,startField = "begin",endField = "end",format = "yyyy-MM-dd HH:ss:mm")
    private String end;

}
