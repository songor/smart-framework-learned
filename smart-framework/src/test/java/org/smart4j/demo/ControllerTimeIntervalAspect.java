package org.smart4j.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalTime;

@Aspect(Controller.class)
public class ControllerTimeIntervalAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTimeIntervalAspect.class);

    private LocalTime begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        begin = LocalTime.now();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        long duration = Duration.between(begin, LocalTime.now()).toMillis();
        LOGGER.info("class: " + cls.getName() + ", method: " + method.getName() + ", duration: " + duration + "ms");
    }

}
