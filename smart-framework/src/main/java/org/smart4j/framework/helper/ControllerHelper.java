package org.smart4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 维护请求 {@link Request} 与处理器 {@link Handler} 的映射关系
 */
public final class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    /**
     * 通过 {@link ClassHelper} 来获取所有带有 {@link Controller} 注解的类，接着遍历这些类，从 {@link Action} 注解中提取 URL，
     * 最后初始化 {@link Request} 与 {@link Handler} 的映射关系
     */
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] controllerMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(controllerMethods)) {
                    for (Method controllerMethod : controllerMethods) {
                        if (controllerMethod.isAnnotationPresent(Action.class)) {
                            Action action = controllerMethod.getAnnotation(Action.class);
                            if (!(controllerMethod.getReturnType() == Data.class ||
                                    controllerMethod.getReturnType() == View.class)) {
                                LOGGER.error("Incorrect pattern for @Action, class is: " + controllerClass.getName()
                                        + ", method is: " + controllerMethod.getName()
                                        + ", returnType is: " + controllerMethod.getReturnType().getName());
                                throw new RuntimeException("Incorrect @Action method define");
                            }
                            String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                String requestMethod = array[0];
                                String requestPath = array[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerClass, controllerMethod);
                                ACTION_MAP.put(request, handler);
                            } else {
                                LOGGER.error("Incorrect pattern for @Action, class is: " + controllerClass.getName()
                                        + ", method is: " + controllerMethod.getName() + ", mapping is: " + mapping);
                                throw new RuntimeException("Incorrect @Action pattern");
                            }
                        }
                    }
                } else {
                    LOGGER.warn("This @Controller Bean does not contains any methods: " + controllerClass.getName());
                }
            }
        } else {
            LOGGER.warn("Smart framework does not manage any @Controller Bean");
        }
        if (MapUtils.isEmpty(ACTION_MAP)) {
            LOGGER.warn("Smart framework does not manage any web request URI");
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
