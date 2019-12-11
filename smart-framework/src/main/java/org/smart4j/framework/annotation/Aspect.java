package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Advisor
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * Pointcut
     */
    Class<? extends Annotation> value();

}
