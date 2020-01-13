package com.lh.validation.annotation;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @Author liuhh    @Date 2020/1/10 9:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Constraint(validatedBy = DateValidator.class)
public @interface DateValid{

    Class<?> value() default  Object.class;

    String name();

}
