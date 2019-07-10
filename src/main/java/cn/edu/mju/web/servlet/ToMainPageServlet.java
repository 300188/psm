package cn.edu.mju.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/toMainPage")
public class ToMainPageServlet extends BaseServlet {

    protected void toMainPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request,response);
    }
}
