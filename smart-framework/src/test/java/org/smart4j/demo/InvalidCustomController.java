package org.smart4j.demo;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Param;

public class InvalidCustomController {

    /**
     * 注入未被框架管理的 Bean
     */
    @Inject
    CustomServiceWithoutAnnotation customService;

    /**
     * 不正确的格式
     */
    @Action("get:/custom-invalid-pattern")
    public Data custom(Param param) {
        return new Data("Invalid Pattern");
    }

}
