package com.lh.validation.annotation;

import javax.validation.Payload;
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
    String startField();
    @NotNull
    String endField();
    @NotNull
    boolean isStart();

    String format() default "yyyy-MM";

    String message() default "开始时间不能大于结束时间";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
