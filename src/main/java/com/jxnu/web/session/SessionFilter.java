package com.jxnu.web.session;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class SessionFilter implements Filter {
    private int expired = 1800;
    private String domain = "";

    public void destroy() {

    }

    private void setCookie(String key, String value, int expired, HttpServletResponse resp) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(expired);
        resp.addCookie(cookie);
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) arg0;
        HttpRequestWrapper wrapper = new HttpRequestWrapper(httpRequest);
        HttpServletResponse resp = ((HttpServletResponse) arg1);

        String key = "";
        if (StringUtils.isNotBlank(key)) {
            wrapper.setKey(key);
            setCookie("key", key, expired, resp);
        } else {
            key = UUID.randomUUID().toString();
            wrapper.setKey(key);
            setCookie("key", key, expired, resp);
        }
        try {
            chain.doFilter(wrapper, arg1);
        } catch (Exception e) {
        } finally {
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }
}

