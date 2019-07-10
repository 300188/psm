package cn.edu.mju.web.servlet.department;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.entity.Department;
import cn.edu.mju.entity.Permission;
import cn.edu.mju.entity.Position;
import cn.edu.mju.service.DepartmentService;
import cn.edu.mju.service.PermissionService;
import cn.edu.mju.service.PositionService;
import cn.edu.mju.service.serviceImpl.DepartmentServiceImpl;
import cn.edu.mju.service.serviceImpl.PermissionServiceImpl;
import cn.edu.mju.service.serviceImpl.PositionServiceImpl;
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

@WebServlet("/department/*")
public class DepartmentServlet extends BaseServlet {

    private PermissionService permissionService = new PermissionServiceImpl();

    private DepartmentService departmentService = new DepartmentServiceImpl();
    private PositionService positionService = new PositionServiceImpl();


    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if(id==null||"".equals(id)){
            response.sendRedirect("/toMainPage");
        }else{
            request.getRequestDispatcher("/WEB-INF/jsp/department/add.jsp").forward(request,response);
        }
    }




    public void assign(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uid = request.getParameter("id");
        if(uid==null||"".equals(uid)){
            response.sendRedirect("/department/index");
        }else{
            Integer id = Integer.parseInt(uid);
            HttpSession session = request.getSession();
            Department department = departmentService.queryById(id);
            session.setAttribute("department",department);
            List<Position> positions = positionService.queryAll();
            List<Position> unassignPositions = new ArrayList<Position>();
            List<Position> assingedPositions = new ArrayList<Position>();
            //获取关系表的数据
            List<Integer> positionids = departmentService.queryPositionidsByDepartmentid(id);
            for (Position position : positions) {
                if(positionids.contains(position.getId())) {
                    assingedPositions.add(position);
                }else {
                    unassignPositions.add(position);
                }
            }

            session.setAttribute("unassignPositions",unassignPositions);
            session.setAttribute("assingedPositions",assingedPositions);

            request.getRequestDispatcher("/WEB-INF/jsp/department/assign.jsp").forward(request,response);

        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String departmentid = request.getParameter("id");
        AJAXResult result = new AJAXResult();
        if(departmentid==null||"".equals(departmentid)){
            result.setSuccess(false);
        }else{
            try {
                Integer id = Integer.parseInt(departmentid);
                departmentService.deleteDepartmentById(id);
                result.setSuccess(true);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


    public void doAssign(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String departmentid = request.getParameter("departmentid");
        String[] unassignPositionids = request.getParameterValues("unassignPositionids");
        AJAXResult result = new AJAXResult();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("departmentid", Integer.parseInt(departmentid));
            paramMap.put("positionids", unassignPositionids);
            departmentService.insertDepartmentPositions(paramMap);

            result.setSuccess(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }

    public void dounAssign(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String departmentid = request.getParameter("departmentid");
        String[] assignPositionids = request.getParameterValues("assignPositionids");
        AJAXResult result = new AJAXResult();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("departmentid", Integer.parseInt(departmentid));
            paramMap.put("positionids", assignPositionids);
            departmentService.deleteDepartmentPositions(paramMap);

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");
        if(id==null||"".equals(id)){
            response.sendRedirect("/toMainPage");
        }else{
            try{
                Integer departmentid = Integer.parseInt(id);

                Department department = departmentService.queryById(departmentid);
                if(department!=null){
                    request.getSession().setAttribute("department",department);
                    request.getRequestDispatcher("/WEB-INF/jsp/department/edit.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/toMainPage");
                }

            }catch (Exception e){
                response.sendRedirect("/toMainPage");

            }
        }



    }


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/department/index.jsp").forward(request,response);
    }


    public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name" );
        String pid = request.getParameter("pid");
        AJAXResult result = new AJAXResult();
        try{
            if(name!=null&&pid!=null){
                Department department = new Department(name,Integer.parseInt(pid));
                try {
                    Department parent = departmentService.queryById(Integer.parseInt(pid));
                    if(parent!=null){
                        department.setName(parent.getName()+">"+name);
                    }

                    Department dbDepartment = departmentService.queryByName(department.getName());

                    if(dbDepartment!=null){
                        result.setSuccess(false);
                    }else{
                        departmentService.insert(department);
                        result.setSuccess(true);
                    }

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
        List<Department> departments = new ArrayList<Department>();
        // 不用递归 提高效率
        List<Department> ds = departmentService.queryAll();

        Map<Integer, Department> departmentMap = new HashMap<Integer, Department>();
        for (Department d : ds) {
            departmentMap.put(d.getId(), d);
        }
        for (Department d : ds) {
            Department child = d;
            if (child.getPid() == 0) {
                departments.add(d);
            } else {
                Department parent = departmentMap.get(child.getPid());
                parent.getChildren().add(child);
            }

        }

        response.getWriter().write(JSON.toJSONString(departments));

    }


    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");
        String name = request.getParameter("name");

        AJAXResult result = new AJAXResult();
        if(name==null||"".equals(name)){
            result.setSuccess(false);
        }else{
            try {
                Integer departmentid = Integer.parseInt(id);
                Department department = new Department();
                department.setId(departmentid);
                department.setName(name);
                departmentService.update(department);
                result.setSuccess(true);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }

        response.getWriter().write(JSON.toJSONString(result));
    }



}
