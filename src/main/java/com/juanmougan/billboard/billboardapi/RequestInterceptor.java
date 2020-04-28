package com.juanmougan.billboard.billboardapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter {

    // TODO maybe move somewhere else
    public static final String TENANT_ID_HEADER = "X-TenantID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        log.info("In preHandle we are Intercepting the Request");
        log.info("____________________________________________");
        String requestURI = request.getRequestURI();
        String tenantID = request.getHeader(TENANT_ID_HEADER);
        log.info("RequestURI::{}|| Search for X-TenantID  :: {}", tenantID, requestURI);
        log.info("____________________________________________");
        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }

}
