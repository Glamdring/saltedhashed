package com.saltedhashed.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {

        // the stacktrace will be printed by spring's DispatcherServlet
        // we are only logging the request url and headeres here
        logger.warn("An exception occurred when invoking the following URL: "
                + request.getRequestURL() + " . Requester IP is "
                + request.getRemoteAddr() + ", User-Agent: "
                + request.getHeader("User-Agent") + "; Message=" + ex.getMessage() + ": " + ex.getStackTrace()[0].getMethodName());

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return null;
    }
}
