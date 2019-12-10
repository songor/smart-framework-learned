package org.smart4j.demo;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;

import java.util.HashMap;

@Controller
public class CustomController {

    @Inject
    private CustomService customService;

    public CustomService getCustomService() {
        return customService;
    }

    @Action("get:/custom")
    public Data custom(Param param) {
        return new Data(new CustomResult("It's a custom message"));
    }

    @Action("get:/custom_empty")
    public Data customWithEmpty(Param param) {
        return new Data(null);
    }

    @Action("get:/display")
    public View display(Param param) {
        return new View("display", new HashMap<>());
    }

    @Action("get:/incorrect_view_path")
    public View incorrectViewPath(Param param) {
        return new View("", new HashMap<>());
    }

}
