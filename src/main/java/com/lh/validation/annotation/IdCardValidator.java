package com.lh.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author liuhh    @Date 2019/12/12 11:33
 */
public class IdCardValidator implements ConstraintValidator<IdCard,Object> {

    public void initialize(IdCard constraintAnnotation) {

    }

    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return IdCardValidator.is18ByteIdCardComplex(o.toString());
    }

    private static boolean is18ByteIdCardComplex(String idCard) {
        String reg = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        return idCard.matches(reg);
    }
}
