package com.library.config;

import com.library.service.SysLogService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OperationLogInterceptorTest {

    private final OperationLogInterceptor interceptor = new OperationLogInterceptor(mock(SysLogService.class));

    @Test
    void preHandleMarksAdminWriteRequestWhenAppHasContextPath() {
        MockHttpServletRequest request = adminRequest("POST", "/api/admin/books");

        interceptor.preHandle(request, null, new Object());

        assertThat(request.getAttribute("operationLogStartTime")).isInstanceOf(Long.class);
    }

    @Test
    void preHandleSkipsLogEndpointItself() {
        MockHttpServletRequest request = adminRequest("POST", "/api/admin/logs");

        interceptor.preHandle(request, null, new Object());

        assertThat(request.getAttribute("operationLogStartTime")).isNull();
    }

    @Test
    void preHandleSkipsReadOnlyAdminRequest() {
        MockHttpServletRequest request = adminRequest("GET", "/api/admin/books");

        interceptor.preHandle(request, null, new Object());

        assertThat(request.getAttribute("operationLogStartTime")).isNull();
    }

    private MockHttpServletRequest adminRequest(String method, String servletPath) {
        MockHttpServletRequest request = new MockHttpServletRequest(method, "/library" + servletPath);
        request.setContextPath("/library");
        request.setServletPath(servletPath);
        return request;
    }
}
