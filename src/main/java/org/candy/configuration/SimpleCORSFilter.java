package org.candy.configuration;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,  DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Origin, Authorization, X-Auth-Token, X-Requested-With");
        response.addHeader("Access-Control-Expose-Headers", "X-Auth-Token");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}
}