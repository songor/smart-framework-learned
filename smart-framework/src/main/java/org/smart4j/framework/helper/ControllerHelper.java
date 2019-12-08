package org.smart4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 管理请求与处理映射关系
 */
public final class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] controllerMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(controllerMethods)) {
                    for (Method controllerMethod : controllerMethods) {
                        if (controllerMethod.isAnnotationPresent(Action.class)) {
                            Action action = controllerMethod.getAnnotation(Action.class);
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
