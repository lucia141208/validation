package com.lh.validation.annotation;

import java.lang.annotation.*;

/**
 * Created by liuhuanhuan on 2020/1/7.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    /**
     * 列头名称
     * @return
     */
    String title() default "";

    /**
     * 下标
     * @return
     */
    int index() default 0;

    /**
     * 数据类型
     * @return
     */
    Class dataType() default String.class;
}
