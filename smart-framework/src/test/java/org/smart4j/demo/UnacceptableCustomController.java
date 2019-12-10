package org.smart4j.demo;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Param;

public class UnacceptableCustomController {

    /**
     * 无效的方法返回类型
     */
    @Action("get:/unacceptable_method_return_type")
    public void unacceptableMethodReturnType(Param param) {
    }

    /**
     * 无效的方法参数类型
     */
    @Action("get:/unacceptable_method_param_type")
    public Data unacceptableMethodParamType() {
        return new Data("Unacceptable Method Param Type");
    }

}
