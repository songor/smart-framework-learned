package org.smart4j.demo;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CustomController {

    @Inject
    private CustomService customService;

    public CustomService getCustomService() {
        return customService;
    }

    @Action("get:/custom")
    public Data custom() {
        return new Data(new CustomResult("It's a custom message"));
    }

    @Action("get:/custom_empty")
    public Data customWithEmpty() {
        return new Data(null);
    }

    @Action("get:/display")
    public View display() {
        return new View("display", new HashMap<>());
    }

    @Action("get:/incorrect_view_path")
    public View incorrectViewPath() {
        return new View("", new HashMap<>());
    }

    @Action("get:/custom_with_param")
    public Data customWithParam(Param param) {
        Map<String, Object> paramMap = param.getParamMap();
        String obj = paramMap.keySet().stream().map(key -> key + "=" + paramMap.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return new Data(obj);
    }

}
