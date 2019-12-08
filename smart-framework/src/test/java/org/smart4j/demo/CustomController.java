package org.smart4j.demo;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;

@Controller
public class CustomController {

    @Inject
    private CustomService customService;

    public CustomService getCustomService() {
        return customService;
    }

}
