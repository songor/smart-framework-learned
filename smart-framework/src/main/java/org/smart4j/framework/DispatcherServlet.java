package org.smart4j.framework;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) {
        // 初始化 Helper 类
        HelperLoader.init();
        // 获取 ServletContext 对象，用于注册 Servlet
        ServletContext servletContext = config.getServletContext();
        // 注册处理 JSP 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    /**
     * 从 {@link HttpServletRequest} 对象中获取请求方法与请求路径，通过 {@link ControllerHelper#getHandler} 方法来获取 {@link Handler} 对象。
     * 拿到 {@link Handler} 对象后，我们可以方便地获取 {@link Controller} 的类，通过 {@link BeanHelper#getBean} 方法获取 {@link Controller} 的实例对象。
     * 随后可以从 {@link HttpServletRequest} 对象中获取所有的请求参数，并将其初始化到 {@link Param} 对象中。
     * 从 {@link Handler} 对象中获取 {@link Action} 方法，然后使用 {@link ReflectionUtil#invokeMethod} 执行。
     * 通过 {@link Action} 方法的返回值类型进行相应的处理，如果是 {@link View} 类型的视图对象，则返回一个 JSP 页面。
     * 如果是 {@link Data} 类型的数据对象，则返回一个 JSON 对象。
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    String paramValue = request.getParameter(paramName);
                    paramMap.put(paramName, paramValue);
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            Class<?> returnType = actionMethod.getReturnType();
            if (View.class == returnType) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                } else {
                    LOGGER.error("requestMethod: " + requestMethod + ", requestPath: " + requestPath);
                    LOGGER.error("controllerClass: " + controllerClass.getName() + ", actionMethod: " + actionMethod.getName()
                            + ", returnType: " + returnType.getName());
                    throw new RuntimeException("@View path incorrect");
                }
            } else if (Data.class == returnType) {
                Data data = (Data) result;
                Object model = data.getModel();
                String json = "";
                if (model != null) {
                    json = JsonUtil.toJson(model);
                }
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                try (PrintWriter writer = response.getWriter()) {
                    writer.write(json);
                    writer.flush();
                } catch (IOException e) {
                    throw e;
                }
            } else {
                throw new RuntimeException("Unreachable");
            }
        } else {
            LOGGER.error("requestMethod: " + requestMethod + ", requestPath: " + requestPath);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
