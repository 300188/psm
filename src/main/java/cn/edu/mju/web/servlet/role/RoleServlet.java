package cn.edu.mju.web.servlet.role;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Role;
import cn.edu.mju.service.RoleService;
import cn.edu.mju.service.serviceImpl.RoleServiceImpl;
import cn.edu.mju.web.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//重构的servlet整合

@WebServlet("/role/*")
public class RoleServlet extends BaseServlet {

    private RoleService roleService = new RoleServiceImpl();


    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/role/add.jsp").forward(request,response);
    }

    public void assign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if(id==null||"".equals(id)){
            response.sendRedirect("/toMainPage");
        }else{
            request.getRequestDispatcher("/WEB-INF/jsp/role/assign.jsp").forward(request,response);
        }
    }


    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = null;

        try{
            //拿到要删除的用户ID
            String id = request.getParameter("id");
            if(!"".equals(id)&&id!=null){
                Integer userid = Integer.parseInt(id);

                int tag = roleService.deleteRoleById(userid);
                if(tag>0){
                    result = new AJAXResult(true);
                }
            }

            if(null == result){
                result = new AJAXResult(false);
            }
        }catch (Exception e){
            result.setSuccess(false);
        }
        response.getWriter().write(JSON.toJSONString(result));

    }


    public void deletes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AJAXResult result = new AJAXResult();
        String[] roleids = request.getParameterValues("roleid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("roleids", roleids);
            int tag = roleService.deleteRoles(map);
            if(tag>0){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
            }
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        response.getWriter().write(JSON.toJSONString(result));
    }


    public void doAssign(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String roleid = request.getParameter("roleid");
        String[] permissionids = request.getParameterValues("permissionids");
        AJAXResult result = new AJAXResult();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("roleid", Integer.parseInt(roleid));
            paramMap.put("permissionids", permissionids);
            roleService.insertRolePermission(paramMap);
            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        response.getWriter().write(JSON.toJSONString(result));


    }


    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id= request.getParameter("id");
        if(id!=null&&!"".equals(id)){
            try{

                Integer roleid = Integer.parseInt(id);
                Role role = roleService.queryById(roleid);
                if(role!=null){
                    request.getSession().setAttribute("role",role);
                    request.getRequestDispatcher("/WEB-INF/jsp/role/edit.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/toMainPage");
                }

            }catch (Exception e) {
                response.sendRedirect("/toMainPage");

            }
        }else{
            response.sendRedirect("/toMainPage");
        }
    }


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/role/index.jsp").forward(request,response);
    }


    public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {


        AJAXResult result = new AJAXResult();
        try{
            String name = request.getParameter("name");
            long createTime  = System.currentTimeMillis();
            Role role = roleService.getRoleByName(name);
            if(role!=null){
                result.setSuccess(false);
            }else{
                role = new Role(name,String.valueOf(createTime));
                int tag = roleService.insert(role);
                if(tag>0){
                    result.setSuccess(true);
                }else{
                    result.setSuccess(false);
                }

            }
        }catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }


    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //开始的页数
        Integer pageno = Integer.parseInt(request.getParameter("pageno"));
        //大小
        Integer pagesize = Integer.parseInt(request.getParameter("pagesize"));
        //模糊查询的内容
        String queryText = request.getParameter("queryText");
        AJAXResult result = new AJAXResult();
        try {
            //分页查询
            Map<String,Object> map = new HashMap<>();
            map.put("start",(pageno-1)*pagesize);
            map.put("size",pagesize);
            map.put("queryText",queryText);
            List<Role> roles = roleService.pageQueryData(map);
            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = roleService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Role> rolePage = new Page<>();
            rolePage.setDatas(roles);
            rolePage.setTotalno(totalno);
            rolePage.setPageno(pageno);
            rolePage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(rolePage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }


    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = new AJAXResult();
        try{
            String name = request.getParameter("name");
            long createTime  = System.currentTimeMillis();
            Integer id = Integer.parseInt(request.getParameter("id"));
            if(id==null){
                result.setSuccess(false);
            }else{
                Role role = new Role(id,name,String.valueOf(createTime));
                int tag = roleService.update(role);
                if(tag>0){
                    result.setSuccess(true);
                }else{
                    result.setSuccess(false);
                }

            }
        }catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }


}
