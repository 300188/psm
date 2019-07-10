package cn.edu.mju.web.servlet;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.entity.Permission;
import cn.edu.mju.entity.User;
import cn.edu.mju.enums.DictionaryEnum;
import cn.edu.mju.service.PermissionService;
import cn.edu.mju.service.UserService;
import cn.edu.mju.service.serviceImpl.PermissionServiceImpl;
import cn.edu.mju.service.serviceImpl.UserServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//登录
@WebServlet("/loginServlet")
public class LoginServlet extends BaseServlet {

    private UserService userService  =  new UserServiceImpl();

    private User checkLogin(String loginAccount,String password){
        return userService.checkLoginUser(loginAccount,password);
    }


    public void loginServlet(HttpServletRequest request, HttpServletResponse response) {
        //登录账号
        String loginAccount = request.getParameter("loginAccount");
        //密码
        String password = request.getParameter("password");
        //验证码
        String code = request.getParameter("validateCode");
        HttpSession session = request.getSession();
        //原始验证码
        String sessionCode = (String) session.getAttribute("validateCode");
        //是否记住登录状态  true|false
        String remember = request.getParameter("remember");
        AJAXResult result = null;
        User user = null;
        if (code == null || "".equals(code) || sessionCode == null || !sessionCode.toUpperCase().equals(code.toUpperCase())) {
            result = new AJAXResult(DictionaryEnum.CODE_ERROR.getMsg(),false);
        }else{
                user = checkLogin(loginAccount,password);
                if(null==user){
                    result = new AJAXResult(DictionaryEnum.NONE.getMsg(),false);
                }else{
                    session.setAttribute("loginUser",user);
                    session.setAttribute("username", user.getUsername());
                    //获取权限
                    this.getPermission(session,user);
                    result = new AJAXResult(DictionaryEnum.SUCCESS.getMsg(),true);
                    setCookie(request,response,remember,loginAccount,password);
                }
        }
        try {
            String jsonString = JSON.toJSONString(result);
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getPermission(HttpSession session,User user){
        //获取用户权限信息
        PermissionService permissionService = new PermissionServiceImpl();
        List<Permission> permissions = permissionService.queryPermissionsByUser(user);
        Permission root = null;
        Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
        Set<String> uriSet = new HashSet<String>();

        for (Permission permission : permissions) {
            permissionMap.put(permission.getId(),permission);
            if(permission.getUrl()!=null&&!"".equals(permission.getUrl())) {
                uriSet.add(session.getServletContext().getContextPath()+permission.getUrl());
            }

        }
        session.setAttribute("authUriSet", uriSet);

        for (Permission permission : permissions) {
            Permission child = permission;
            if(child.getPid()==0) {
                root = permission;
            }else {
                Permission parent = permissionMap.get(child.getPid());
                parent.getChildren().add(child);
            }

        }
        session.setAttribute("rootPermission",root);

    }


//记住登录账户
    private void setCookie(HttpServletRequest request,HttpServletResponse response,String remember,String loginAccount,String password){
        if("true".equals(remember)) {
//                todo
//                加密
            Cookie cookie1 = new Cookie("loginAccount", loginAccount);
            cookie1.setMaxAge(60 * 60 * 24 * 3);
            cookie1.setPath("/");
            Cookie cookie2 = new Cookie("password",password);
            cookie2.setMaxAge(60 * 60 * 24 * 3);
            cookie2.setPath("/");
            response.addCookie(cookie1);
            response.addCookie(cookie2);
        }else{
            Cookie cookie1 = new Cookie("loginAccount", null);
            Cookie cookie2 = new Cookie("password", null);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
        }
    }
}
