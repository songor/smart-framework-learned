package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射工具类
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.getConstructor().newInstance();
        } catch (Exception e) {
            LOGGER.error("cls: " + cls.getName());
            throw new RuntimeException("New instance failure", e);
        }
        return instance;
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("obj class: " + obj.getClass().getName() + ", obj: " + obj + ", method: " + method.getName()
                    + ", args: " + Arrays.toString(args));
            throw new RuntimeException("Invoke method failure", e);
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("obj class: " + obj.getClass().getName() + ", obj: " + obj + ", field: " + field.getName()
                    + ", value: " + value);
            throw new RuntimeException("Set field failure", e);
        }
    }

}
