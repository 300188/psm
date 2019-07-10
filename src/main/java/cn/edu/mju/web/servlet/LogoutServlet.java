package cn.edu.mju.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logoutServlet")
public class LogoutServlet extends BaseServlet {

    protected void logoutServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        response.sendRedirect("/toLoginPage");
    }
}
