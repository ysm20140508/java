package com.jxnu.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 跨域解决
 *
 * @author shoumiao_yao
 * @date 2016-07-22
 */
public class CORSFilter implements Filter {
    String regex = "http://[^/]*";

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        //删除
        String refer = req.getHeader("Referer");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Pattern p = Pattern.compile(regex);
        if (StringUtils.isNotBlank(refer)) {
            Matcher m = p.matcher(refer);
            if (m.find()) {
                String host = m.group();
                resp.setHeader("Access-Control-Allow-Origin", host);
            }
        }
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, resp);
    }

    public void destroy() {
    }
}
