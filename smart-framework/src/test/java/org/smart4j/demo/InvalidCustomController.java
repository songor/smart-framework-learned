package org.smart4j.demo;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Inject;

public class InvalidCustomController {

    /**
     * 注入未被框架管理的 Bean
     */
    @Inject
    CustomServiceWithoutAnnotation customService;

    /**
     * 不正确的格式
     */
    @Action("/custom")
    public void custom() {

    }

}
