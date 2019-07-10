package cn.edu.mju.web.filter;

import cn.edu.mju.entity.Permission;
import cn.edu.mju.service.PermissionService;
import cn.edu.mju.service.serviceImpl.PermissionServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//控制访问权限
public class PermissionFilter implements Filter {

    private PermissionService permissionService = new PermissionServiceImpl();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String username = (String) request.getSession().getAttribute("username");
        if(null == username || "".equals(username)){
            response.sendRedirect("/toLoginPage");
        }else{
            if(this.checkPermission(request,response)){
                request.getSession().setAttribute("state","1");
                chain.doFilter(request, response);
            }else{
                response.sendRedirect("/toMainPage");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

    public boolean checkPermission(HttpServletRequest request, HttpServletResponse response)  {

        // TODO Auto-generated method stub
        //获取用户的请求地址
        String uri = request.getRequestURI();

        //判断当前的路径是否需要进行权限拦截

        String path = request.getSession().getServletContext().getContextPath();
        //查询所需要验证的路径集合
        List<Permission> permissions = permissionService.queryAll();
        Set<String> uriSet = new HashSet<String>();
        for (Permission permission : permissions) {
            if(permission.getUrl() != null && !permission.getUrl().equals("")) {
                uriSet.add(path+permission.getUrl());
            }
        }

        if(uriSet.contains(uri)) {
            //权限验证
            //判断当前的用户是否拥有对应的权限
            Set<String> authUriSet = (Set<String>) request.getSession().getAttribute("authUriSet");
            if(authUriSet.contains(uri)) {
                return true;
            }else {
                return false;
            }

        }else {
            return true;
        }
    }

}
