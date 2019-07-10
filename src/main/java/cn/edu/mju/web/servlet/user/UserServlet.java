package cn.edu.mju.web.servlet.user;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Role;
import cn.edu.mju.entity.User;
import cn.edu.mju.service.RoleService;
import cn.edu.mju.service.UserService;
import cn.edu.mju.service.serviceImpl.RoleServiceImpl;
import cn.edu.mju.service.serviceImpl.UserServiceImpl;
import cn.edu.mju.web.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    private RoleService roleService = new RoleServiceImpl();



    public void addPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/user/add.jsp").forward(request,response);
    }




    public void assign(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uid = request.getParameter("id");
        if(uid==null||"".equals(uid)){
            response.sendRedirect("/user/index");
        }else{
            Integer id = Integer.parseInt(uid);
            HttpSession session = request.getSession();
            User user = userService.queryById(id);
            session.setAttribute("user",user);
            List<Role> roles = roleService.queryAll();
            List<Role> unassignRoles = new ArrayList<Role>();
            List<Role> assingedRoles = new ArrayList<Role>();
            //获取关系表的数据
            List<Integer> roleids = userService.queryRoleidsByUserid(id);
            for (Role role : roles) {
                if(roleids.contains(role.getId())) {
                    assingedRoles.add(role);
                }else {
                    unassignRoles.add(role);
                }
            }

            session.setAttribute("unassignRoles",unassignRoles);
            session.setAttribute("assingedRoles",assingedRoles);

            request.getRequestDispatcher("/WEB-INF/jsp/user/assign.jsp").forward(request,response);

        }
    }


    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = null;

        //拿到要删除的用户ID
        String id = request.getParameter("id");
        if(!"".equals(id)&&id!=null){
            Integer userid = Integer.parseInt(id);

            int tag = userService.deleteUserById(userid);
            if(tag>0){
                result = new AJAXResult(true);
            }
        }

        if(null == result){
            result = new AJAXResult(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }


    public void deletes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AJAXResult result = new AJAXResult();
        String[] userids = request.getParameterValues("userid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("userids", userids);
            int tag = userService.deleteUsers(map);
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

        String userid = request.getParameter("userid");
        String[] unassignroleids = request.getParameterValues("unassignroleids");
        AJAXResult result = new AJAXResult();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userid", Integer.parseInt(userid));
            paramMap.put("roleids", unassignroleids);
            userService.insertUserRoles(paramMap);

            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }


    public void dounAssign(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userid = request.getParameter("userid");
        String[] assignroleids = request.getParameterValues("assignroleids");
        AJAXResult result = new AJAXResult();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("userid", Integer.parseInt(userid));
            paramMap.put("roleids", assignroleids);
            userService.deleteUserRoles(paramMap);

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


    public void editPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/user/edit.jsp").forward(request, response);
    }


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/user/index.jsp").forward(request,response);

    }

    public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {


        AJAXResult result = new AJAXResult();
        String loginAccount = request.getParameter("loginacct");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        long createTime  = System.currentTimeMillis();
        User user = userService.getUserByLoginAccount(loginAccount);
        if(user!=null){
            result.setSuccess(false);
        }else{
            user = new User(username,loginAccount,password,email,String.valueOf(createTime));
            int tag = userService.insert(user);
            if(tag>0){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
            }

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
            List<User> users = userService.pageQueryData(map);
            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = userService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<User> userPage = new Page<>();
            userPage.setDatas(users);
            userPage.setTotalno(totalno);
            userPage.setPageno(pageno);
            userPage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(userPage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }



    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = new AJAXResult();
        String loginAccount = request.getParameter("loginacct");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        long createTime  = System.currentTimeMillis();
        Integer id = Integer.parseInt(request.getParameter("id"));
        if(id==null){
            result.setSuccess(false);
        }else{
            User user = new User(id,username,loginAccount,password,email,String.valueOf(createTime));
            int tag = userService.update(user);
            if(tag>0){
                result.setSuccess(true);
                request.getSession().setAttribute("loginUser",user);
                request.getSession().setAttribute("username", user.getUsername());
            }else{
                result.setSuccess(false);
            }

        }
        response.getWriter().write(JSON.toJSONString(result));


    }



}
