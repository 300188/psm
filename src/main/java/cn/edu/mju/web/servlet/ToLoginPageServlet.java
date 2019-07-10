package cn.edu.mju.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/toLoginPage")
public class ToLoginPageServlet extends BaseServlet {

    public void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if(null == username || "".equals(username)){
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        }else{
            response.sendRedirect("/toMainPage");
        }
    }
}
