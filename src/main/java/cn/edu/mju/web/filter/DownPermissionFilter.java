package cn.edu.mju.web.filter;

import cn.edu.mju.entity.Log;
import cn.edu.mju.service.LogService;
import cn.edu.mju.service.serviceImpl.LogServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownPermissionFilter implements Filter {

    private LogService logService = new LogServiceImpl();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String state = (String) request.getSession().getAttribute("state");
        if(state==null||!"1".equals(state)){
            response.sendRedirect("/toMainPage");
        }else{
            //记录日志
            Log log  = new Log();
            log.setCreateTime(String.valueOf(System.currentTimeMillis()));
            log.setLog(request.getSession().getAttribute("username")+" "+request.getRequestURI());
            logService.deleteByLog(log);
            logService.insert(log);
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
