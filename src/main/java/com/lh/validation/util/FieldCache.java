package com.lh.validation.util;

import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liuhuanhuan on 2020/1/7.
 */
public class FieldCache {

    private static ThreadLocal<Map<String, Field>> FIELDS = new ThreadLocal<Map<String, Field>>();

    /**
     * 获取field 属性
     *
     * @param cla
     * @param fieldName
     * @return
     */
    public static Field getField(Object cla, String fieldName) {
        Map<String, Field> objectField;
        if (CollectionUtils.isEmpty(objectField = FIELDS.get())) {
            objectField = Maps.newHashMap();
            Class clazz = cla.getClass();
            while (clazz != null) {
                List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
                objectField.putAll(fields.stream().collect(Collectors.toMap(Field::getName, a -> a, (k1, k2) -> k1)));
                clazz = clazz.getSuperclass();
            }
            FIELDS.set(objectField);
        }
        return objectField.get(fieldName);
    }

    public static void remove() {
        FIELDS.remove();
    }
}
