package com.lh.validation.annotation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author liuhh    @Date 2020/1/10 9:09
 */
@Slf4j
public class DateValidator implements ConstraintValidator<DateValid,Object> {

    @Override
    public void initialize(DateValid date) {
        System.out.println("initialize 进来了。。。");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("isValid 进来了。。。");
        return compareDate(o);
    }

    public static boolean compareDate(Object o) {
        try {
            Map<String, DateCouple> dateMap = parseObject(o);
            if (!dateMap.isEmpty()) {
                boolean flag = true;
                for (DateCouple dateCouple : dateMap.values() ) {
                    if (Objects.nonNull(dateCouple.getStartDate()) && Objects.nonNull(dateCouple.getEndDate())) {
                        if (dateCouple.getStartDate().getTime() > dateCouple.getEndDate().getTime()) {
                            flag = false;
                        }
                    }
                }

                return flag;
            }
        }catch (IllegalAccessException e){
            log.error("时间参数获取异常，请检查配置！");
            return false;
        }catch (ParseException e1){
            log.error("时间参数转化异常");
            return false;
        }

        return true;
    }

    public static boolean compareSingle(DateCouple dateCouple) {

        if (Objects.nonNull(dateCouple.getStartDate()) && Objects.nonNull(dateCouple.getEndDate())) {
            if (dateCouple.getStartDate().getTime() > dateCouple.getEndDate().getTime()) {
                return false;
            }
        }

        return true;
    }

    public static Map<String, DateCouple> parseObject(Object o)  throws IllegalAccessException,ParseException {
        Map<String,DateCouple> dateMap = new HashMap<>();
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(DateRange.class)){
                DateRange dateRange = field.getAnnotation(DateRange.class);
                String format = dateRange.format();
                String key = dateRange.startField()+"-"+dateRange.endField();
                DateCouple dateCouple = new DateCouple();

                if (dateMap.containsKey(key)){
                    dateCouple = dateMap.get(key);
                }else {
                    dateCouple.setDateRange(dateRange);
                    dateMap.put(key,dateCouple);
                }
                Object start = null;
                Object end = null;
                field.setAccessible(true);
                if (dateRange.isStart()){
                    start =field.get(o);
                }else {
                    end = field.get(o);
                }
                checkProperty(format,start,end,dateCouple);
            }
        }
        return dateMap;
    }

    private static void checkProperty(String format, Object start, Object end, DateCouple dateCouple) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (Objects.nonNull(start)){
            if (start instanceof Date ){
                dateCouple.setStartDate((Date) start);
            }else if (start instanceof String){
                dateCouple.setStartDate(sdf.parse((String) start));
            }else if (start instanceof Long || start instanceof Integer){
                dateCouple.setStartDate(new Date((Long) start));
            }
        }
        if (Objects.nonNull(end)){
            if (end instanceof Date){
                dateCouple.setEndDate((Date) end);
            }else if (end instanceof String){
                dateCouple.setEndDate(sdf.parse((String) end));
            }else if (end instanceof Long || end instanceof Integer){
                dateCouple.setEndDate(new Date((Long) end));
            }
        }
    }

    @Data
    public static class DateCouple{
        private Date startDate;
        private Date endDate;
        DateRange dateRange;
    }
}
