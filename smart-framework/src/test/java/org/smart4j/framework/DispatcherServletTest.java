package org.smart4j.framework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("请求转发器测试")
public class DispatcherServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Test
    @DisplayName("返回 JSON 对象")
    public void shouldReturnDataSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("get");
        when(request.getPathInfo()).thenReturn("/custom");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.service(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String result = sw.getBuffer().toString();
        assertThat(result).isEqualTo("{\"message\":\"It's a custom message\"}");
    }

    @Test
    @DisplayName("返回空 JSON 对象")
    public void shouldReturnEmptyDataSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("get");
        when(request.getPathInfo()).thenReturn("/custom_empty");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.service(request, response);

        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String result = sw.getBuffer().toString();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("不匹配的请求路径")
    public void shouldReturn404WhenMismatchedRequestPath() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("get");
        when(request.getPathInfo()).thenReturn("/undefined");

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.service(request, response);

        verify(response).sendError(404);
    }

    @Test
    @DisplayName("未正确赋值的 View 路径")
    public void shouldThrowExceptionWhenIncorrectViewPath() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("get");
        when(request.getPathInfo()).thenReturn("/incorrect_view_path");

        DispatcherServlet servlet = new DispatcherServlet();
        assertThatThrownBy(() -> servlet.service(request, response)).isInstanceOf(RuntimeException.class)
                .hasMessage("@View path incorrect");
    }

}