package cn.edu.mju.web.servlet;

import cn.edu.mju.service.UserService;
import cn.edu.mju.service.serviceImpl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

public class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置编码为UTF-8
        req.setCharacterEncoding("UTF-8");
        //设置响应的编码
        resp.setContentType("text/html; charset=UTF-8");
        //核心方法
        try {
            doDispatch(req,resp);
//            execute(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/404.jsp").forward(req,resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    protected void execute(HttpServletRequest request,HttpServletResponse response) throws Exception{}



    //路由分发
    private void doDispatch(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        try{
            boolean flag = false;
            Class clazz = this.getClass();
            String uri = request.getRequestURI().trim();
            String[] uris = uri.split("/");
            Method[] methods = clazz.getDeclaredMethods();
            for(int i=0;i<uris.length;i++) {
                uris[i] = uris[i].trim();
                for (Method method : methods) {
                    if (method.getName().equals(uris[i])) {
                        method.invoke(this, request, response);
                        flag = true;
                    }

                }
            }

            if(!flag){
                request.getRequestDispatcher("/404.jsp").forward(request,response);
            }
        } catch (Exception e){
            request.getRequestDispatcher("/404.jsp").forward(request,response);
            e.printStackTrace();
        }

    }






}
