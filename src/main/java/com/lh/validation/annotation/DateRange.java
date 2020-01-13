package com.lh.validation.annotation;

import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @Author liuhh    @Date 2020/1/10 9:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateRange {
    @NotNull
    public String startField();
    @NotNull
    public String endField();
    @NotNull
    public boolean isStart();

    public String format() default "yyyy-MM";

    public String message() default "开始时间不能大于结束时间";
}
