package com.lh.validation.resolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lh.validation.annotation.DateRange;
import com.lh.validation.annotation.DateValid;
import com.lh.validation.annotation.DateValidator;
import com.lh.validation.annotation.request.RequestWrapper;
import com.sun.jmx.snmp.Timestamp;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author liuhh    @Date 2020/1/11 12:39
 */
public class DateValidResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(DateValid.class)){
            DateValid dateValid = parameter.getParameterAnnotation(DateValid.class);
            return parameter.getParameterType().isAssignableFrom(dateValid.value())
                    &&parameter.hasParameterAnnotation(DateValid.class);
        }
        return false;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        DateValid dateValid = parameter.getParameterAnnotation(DateValid.class);
        Object obj = null;
        if (dateValid.value().equals(parameter.getParameterType())) {
            //从传进来的webRequest中获取HttpServletRequest对象
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            String methodType = request.getMethod().toUpperCase();
            //创建请求实例
            if ("GET".equals(methodType)){
                obj = createInstanceByGet(dateValid, request);
            }else if ("POST".equals(methodType)){
                obj = createInstanceByPost(dateValid, request);
            }
            Map<String, DateValidator.DateCouple> dateMap = DateValidator.parseObject(obj);
            if (!dateMap.isEmpty()) {
                for (DateValidator.DateCouple dateCouple : dateMap.values()) {
                    if (!DateValidator.compareSingle(dateCouple)){
                        throw new Exception(dateCouple.getDateRange().message());
                    }
                }
            }

        }

        return obj;
    }

    private Object createInstanceByPost(DateValid dateValid, HttpServletRequest request) {
        Object instance = null;
        Class obj = dateValid.value();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        instance = JSONObject.toJavaObject(JSON.parseObject(body),obj);
        return instance;

    }


    private Object createInstanceByGet(DateValid dateValid, HttpServletRequest request) {
        Object instance = null;
        try{
            Class obj = dateValid.value();
            Field[] fields = obj.getDeclaredFields();
            instance = obj.newInstance();
            for (Field field : fields){
                String name = field.getName();
                if (name.equals("serialVersionUID")) {
                    continue;
                }
                Class<?> type = obj.getDeclaredField(name).getType();
                String replace = name.substring(0,1).toUpperCase()+name.substring(1);
                Method setMethod = obj.getMethod("set"+replace,type);
                String str = request.getParameter(replace);
                System.out.println(replace+":"+str);
                if (str == null || "".equals(str)){
                    String small = name.substring(0,1).toLowerCase()+name.substring(1);
                    str = request.getParameter(small);
                    System.out.println(small+":"+str);
                }

                if (str!=null && !"".equals(str)){
                    // ---判断读取数据的类型
                    if (type.isAssignableFrom(String.class)) {
                        setMethod.invoke(instance, str);
                    } else if (type.isAssignableFrom(int.class)
                            || type.isAssignableFrom(Integer.class)) {
                        setMethod.invoke(instance, Integer.parseInt(str));
                    } else if (type.isAssignableFrom(Double.class)
                            || type.isAssignableFrom(double.class)) {
                        setMethod.invoke(instance, Double.parseDouble(str));
                    } else if (type.isAssignableFrom(Boolean.class)
                            || type.isAssignableFrom(boolean.class)) {
                        setMethod.invoke(instance, Boolean.parseBoolean(str));
                    } else if (type.isAssignableFrom(Date.class)) {
                        String format = "yyyy-MM-dd";
                        if (field.isAnnotationPresent(DateRange.class)){
                            DateRange dateRange = field.getAnnotation(DateRange.class);
                            format = dateRange.format();
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                        setMethod.invoke(instance, dateFormat.parse(str));
                    } else if (type.isAssignableFrom(Timestamp.class)) {
                        String format = "yyyy-MM-dd HH:mm:ss";
                        if (field.isAnnotationPresent(DateRange.class)){
                            DateRange dateRange = field.getAnnotation(DateRange.class);
                            format = dateRange.format();
                        }
                        SimpleDateFormat dateFormat=new SimpleDateFormat(format);
                        setMethod.invoke(instance, new Timestamp(dateFormat.parse(str).getTime()));
                    }

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }
}
