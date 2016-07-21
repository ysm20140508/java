package com.jxnu.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author shoumiao_yao
 * @date 2016-07-18
 */
public class SessionListener implements HttpSessionListener {
    private final static Object lock = new Object();

    /**
     * 创建session触发
     *
     * @param httpSessionEvent
     */
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext context = session.getServletContext();
        synchronized (lock) {
            Integer onlineCount = (Integer) context.getAttribute("onlineCount");
            if (onlineCount == null) {
                context.setAttribute("onlineCount", Integer.valueOf(1));
            } else {
                context.setAttribute("onlineCount", Integer.valueOf(1 + onlineCount.intValue()));
            }
        }

    }

    /**
     * 销毁session触发
     *
     * @param httpSessionEvent
     */
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext context = session.getServletContext();
        synchronized (lock) {
            Integer onlineCount = (Integer) context.getAttribute("onlineCount");
            if (onlineCount == null) {
                return;
            } else if (onlineCount.intValue() > 0) {
                context.setAttribute("onlineCount", Integer.valueOf(onlineCount.intValue() - 1));
            }
        }
    }
}
