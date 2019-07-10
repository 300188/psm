package cn.edu.mju.web.servlet.permission;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.entity.Permission;
import cn.edu.mju.service.PermissionService;
import cn.edu.mju.service.serviceImpl.PermissionServiceImpl;
import cn.edu.mju.web.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/permission/*")
public class PermissionServlet extends BaseServlet {

    private PermissionService permissionService = new PermissionServiceImpl();


    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if(id==null||"".equals(id)){
            response.sendRedirect("/toMainPage");
        }else{
            request.getRequestDispatcher("/WEB-INF/jsp/permission/add.jsp").forward(request,response);
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String permissionid = request.getParameter("id");
        AJAXResult result = new AJAXResult();
        if(permissionid==null||"".equals(permissionid)){
            result.setSuccess(false);
        }else{
            try {
                Integer id = Integer.parseInt(permissionid);
                permissionService.deletePermission(id);
                result.setSuccess(true);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }

        response.getWriter().write(JSON.toJSONString(result));

    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if(id==null||"".equals(id)){
            response.sendRedirect("/toMainPage");
        }else{
            try{
                Integer permissionid = Integer.parseInt(id);
                Permission permission = permissionService.queryById(permissionid);
                if(permission!=null){
                    request.getSession().setAttribute("permission",permission);
                    request.getRequestDispatcher("/WEB-INF/jsp/permission/edit.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/toMainPage");
                }
            }catch (Exception e){
                response.sendRedirect("/toMainPage");

            }
        }
    }


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/permission/index.jsp").forward(request,response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name" );
        String url = request.getParameter("url");
        String pid = request.getParameter("pid");
        AJAXResult result = new AJAXResult();
        try{

            if(name!=null&&pid!=null){
                Permission permission = new Permission(name,url,Integer.parseInt(pid));
                try {
                    permissionService.insertPermission(permission);
                    result.setSuccess(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setSuccess(false);
                }
            }else{
                result.setSuccess(false);
            }
        }catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));



    }



    public void loadAssignData(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String roleid = request.getParameter("roleid");
        if(roleid==null||"".equals(roleid)){
            response.sendRedirect("/user/index");
        }else{
            List<Permission> permissions = new ArrayList<Permission>();
            List<Permission> ps = permissionService.queryAll();

            //获取当前角色已经分配的许可信息
            List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);


            Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
            for (Permission p : ps) {
                if(permissionids.contains(p.getId())){
                    p.setChecked(true);
                }else {
                    p.setChecked(false);
                }
                permissionMap.put(p.getId(), p);
            }
            for (Permission p : ps) {
                Permission child = p;
                if (child.getPid() == 0) {
                    permissions.add(p);
                } else {
                    Permission parent = permissionMap.get(child.getPid());
                    parent.getChildren().add(child);
                }

            }

            response.getWriter().write(JSON.toJSONString(permissions));

        }

    }


    public void loadData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Permission> permissions = new ArrayList<Permission>();
        // 不用递归 提高效率
        List<Permission> ps = permissionService.queryAll();

        Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
        for (Permission p : ps) {
            permissionMap.put(p.getId(), p);
        }
        for (Permission p : ps) {
            Permission child = p;
            if (child.getPid() == 0) {
                permissions.add(p);
            } else {
                Permission parent = permissionMap.get(child.getPid());
                parent.getChildren().add(child);
            }

        }

        response.getWriter().write(JSON.toJSONString(permissions));

    }


    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String url = request.getParameter("url");

        AJAXResult result = new AJAXResult();
        if(name==null||"".equals(name)){
            result.setSuccess(false);
        }else{
            try {
                Integer permissionid = Integer.parseInt(id);
                Permission permission = new Permission();
                permission.setId(permissionid);
                permission.setName(name);
                permission.setUrl(url);
                permissionService.updatePermission(permission);
                result.setSuccess(true);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


}
