package org.smart4j.demo.controller;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Param;

@Controller
public class HelloController {

    @Action("get:/hello")
    public Data hello(Param param) {
        return new Data("hello");
    }

}
