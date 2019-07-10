package cn.edu.mju.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("/PATHListener")
public class PATHListener implements ServletContextListener {

    //将上下文路径放入APP_PATH中 简化静态页面代码
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String APP_PATH = servletContextEvent.getServletContext().getContextPath();
        servletContextEvent.getServletContext().setAttribute("APP_PATH",APP_PATH);
        System.out.println(APP_PATH);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
