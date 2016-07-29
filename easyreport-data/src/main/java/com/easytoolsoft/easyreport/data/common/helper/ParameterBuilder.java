package com.easytoolsoft.easyreport.data.common.helper;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Dao接口中查询参数构造类
 */
public class ParameterBuilder {
    private final Map<String, Object> params = new HashMap<>(6);

    private ParameterBuilder() {
    }

    public static ParameterBuilder getInstance() {
        return new ParameterBuilder();
    }

    /**
     * 获取查询参数Map
     *
     * @param obj pojo对象
     * @return Map[String, Object]
     */
    public static <T> Map<String, Object> getQueryParams(T obj) {
        if (obj == null) {
            return new HashMap<>(0);
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return new HashMap<>(0);
        }

        Map<String, Object> params = new HashMap<>(fields.length);
        for (Field field : fields) {
            Object value = getValue(field, obj);
            if (value != null) params.put(field.getName(), value);
        }
        return params;
    }

    private static <T> Object getValue(Field field, T obj) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException ex) {
            return null;
        }
    }

    public ParameterBuilder add(String name, Object value) {
        params.put(name, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return this.params;
    }
}