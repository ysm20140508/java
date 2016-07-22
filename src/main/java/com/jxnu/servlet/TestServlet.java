package com.jxnu.servlet;

import com.jxnu.util.ApplicationContextUtils;
import com.jxnu.spring.aop.Dao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author shoumiao_yao
 * @date 2016-07-18
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao test = (Dao) ApplicationContextUtils.getBean("test");
        test.test("123","123");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>MyFirstServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Servlet MyFirstServlet at " + req.getContextPath() + "</h2>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
