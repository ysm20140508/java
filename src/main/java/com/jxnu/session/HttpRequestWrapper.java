package com.jxnu.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author shoumiao_yao
 * @date 2016-07-18
 */

/**
 * HttpRequest包装类
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {
    private HttpSessionWrapper session;
    private String key;

    public HttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public HttpSession getSession() {
        if (session == null) {
            return getSession(true);
        }
        return session;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (create) {
            HttpSessionWrapper localSession = null;
            this.session = localSession;
            return this.session;
        } else {
            if (this.session == null) {
                this.session = (HttpSessionWrapper) getSession(true);
            }
            return this.session;
        }
    }
}
