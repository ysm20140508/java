package com.jxnu.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * @author shoumiao_yao
 * @date 2016-07-18
 */
public class HttpSessionWrapper implements HttpSession {
    private SessionBean bean;
    private HttpServletRequest request;
    private String key;

    public HttpSessionWrapper(SessionBean bean, HttpServletRequest request) {
        this.bean = bean;
        this.request = request;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public ServletContext getServletContext() {
        return this.getServletContext();
    }

    public void setMaxInactiveInterval(int interval) {

    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public Object getAttribute(String name) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public void setAttribute(String name, Object value) {

    }

    public void removeAttribute(String name) {

    }

    public void invalidate() {

    }

    public boolean isNew() {
        return false;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getValue(String name) {
        return null;
    }

    public String[] getValueNames() {
        return new String[0];
    }

    public void putValue(String name, Object value) {

    }

    public void removeValue(String name) {

    }
}
