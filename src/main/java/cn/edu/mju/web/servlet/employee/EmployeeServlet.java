package cn.edu.mju.web.servlet.employee;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.*;
import cn.edu.mju.service.ContractService;
import cn.edu.mju.service.DepartmentService;
import cn.edu.mju.service.EmployeeService;
import cn.edu.mju.service.PositionService;
import cn.edu.mju.service.serviceImpl.ContractServiceImpl;
import cn.edu.mju.service.serviceImpl.DepartmentServiceImpl;
import cn.edu.mju.service.serviceImpl.EmployeeServiceImpl;
import cn.edu.mju.service.serviceImpl.PositionServiceImpl;
import cn.edu.mju.utils.ExcelUtils;
import cn.edu.mju.web.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/employee/*")
public class EmployeeServlet extends BaseServlet {

    private EmployeeService employeeService = new EmployeeServiceImpl();

    private DepartmentService departmentService = new DepartmentServiceImpl();

    private PositionService positionService = new PositionServiceImpl();


    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            //获取所有的部门信息
            List<Department> departmentList = departmentService.queryAll();

            //获取所有的职位信息

            request.setAttribute("departmentList",departmentList);

        }catch (Exception e){
        }finally {

            request.getRequestDispatcher("/WEB-INF/jsp/employee/add.jsp").forward(request,response);
        }
    }


    public void assign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        try{
            if(id==null||"".equals(id)){
                response.sendRedirect("/toMainPage");
            }else{
                Integer employeeid = Integer.parseInt(id);
                Employee employee = employeeService.queryById(employeeid);
                if(employee!=null){
                    Information information =  employeeService.quaryInformationByEmployeeId(employeeid);
                    if(information==null){
                        information = new Information();
                    }
                    information.setName(employee.getName());
                    request.getSession().setAttribute("information",information);
                    request.getRequestDispatcher("/WEB-INF/jsp/employee/assign.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/toMainPage");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/toMainPage");

        }

    }



    public void data(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/employee/data.jsp").forward(request,response);
    }


    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String employeeid = request.getParameter("id");

        AJAXResult result = new AJAXResult();

        try{
            if(employeeid==null||"".equals(employeeid)){
                result.setSuccess(false);
            }else{

                Integer id = Integer.parseInt(employeeid);

                //根据id删除对应的员工
                int i = employeeService.deleteById(id);
                if (i>0){
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


    public void deletes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AJAXResult result = new AJAXResult();
        String[] employeeids = request.getParameterValues("employeeid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("employeeids", employeeids);
            int tag = employeeService.deleteEmployees(map);
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


    public void detailDownloadServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try{


            String filename ="informations.xls";

//        String folder = "/upload/";

            //通知浏览器以下载的方式打开

            response.addHeader("Content-Type","application/octet-stream");

            response.addHeader("Content-Disposition","attachment;filename="+filename);

            //获取文件流
//        InputStream in = getServletContext().getResourceAsStream(folder+filename);

            OutputStream out = response.getOutputStream();

            Map<String,Object> map = new HashMap<>();

            List<Information> list = employeeService.queryAllInformation();

            map.put("informations",list);

            ExcelUtils.DetailExport.export(out,map);

//        byte[] buffer = new byte[1024];
//
//        int len;
//
//        while((len = in.read(buffer))!=-1){
//            out.write(buffer,0,len);
//        }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String id= request.getParameter("id");
        if(id!=null&&!"".equals(id)){
            try{
                Integer employeeid = Integer.parseInt(id);
                Employee employee = employeeService.queryById(employeeid);
                if(employee!=null){
                    //获取所有的部门信息
                    List<Department> departmentList = departmentService.queryAll();

                    //获取所有的职位信息

                    request.getSession().setAttribute("employee",employee);
                    request.setAttribute("departmentList",departmentList);

                    request.getRequestDispatcher("/WEB-INF/jsp/employee/edit.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/toMainPage");
                }
            }catch (Exception e){
                response.sendRedirect("/toMainPage");

            }

        }else{
            response.sendRedirect("/toMainPage");
        }
    }


    public void fileDownloadServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try{


            String filename ="employees.xls";

//        String folder = "/upload/";

            //通知浏览器以下载的方式打开

            response.addHeader("Content-Type","application/octet-stream");

            response.addHeader("Content-Disposition","attachment;filename="+filename);

            //获取文件流
//        InputStream in = getServletContext().getResourceAsStream(folder+filename);

            OutputStream out = response.getOutputStream();

            Map<String,Object> map = new HashMap<>();

            List<Employee> list = employeeService.queryAll();

            map.put("employees",list);

            ExcelUtils.JXLExport.export(out,map);

//        byte[] buffer = new byte[1024];
//
//        int len;
//
//        while((len = in.read(buffer))!=-1){
//            out.write(buffer,0,len);
//        }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public void fileUploadServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

//        File file = new File("/fileUploadTemp");
//        if(!file.exists()){
//            file.mkdirs();
//        }
//        diskFileItemFactory.setRepository(file);

        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("utf-8");

        AJAXResult ajaxResult = new AJAXResult();
        try {
            List<FileItem> items = servletFileUpload.parseRequest(request);
            PrintWriter writer = response.getWriter();
            for (FileItem item : items) {

                if (item.isFormField()){
                    //是普通字段
//                    System.out.println(item.getFieldName()+":"+item.getString());

                }else{
                    //是文件
                    //获取上传的文件名
//                    String filename = item.getName();
//                    String webPath = "/upload/";
//                    String filepath=  getServletContext().getRealPath(webPath+filename);
//                    System.out.println(filepath);

//                    File f = new File(filepath);
//                    f.getParentFile().mkdirs();
//                    f.createNewFile();

                    InputStream in =  item.getInputStream();

//                    byte[] buffer = new byte[1024];
////
//                    int len;
//                    FileOutputStream out = new FileOutputStream(f);
////
//                    while((len = in.read(buffer))>0){
//                        out.write(buffer,0,len);
//                    }
                    try{
                        Map<String, Object> map = ExcelUtils.Input.input(in);
                        boolean flag = (boolean) map.get("success");

                        List<Employee> employees = (List<Employee>) map.get("employees");
                        if(flag&&employees.size()>0){

                            for (Employee employee : employees) {

                                Department department = new Department();
                                Position position = new Position();

//                                System.out.println(employee);
                                if("".equals(employee.getName())){
                                    employee.setName("无");
                                }
                                if(!"".equals(employee.getName())){
                                    if(!"".equals(employee.getDepartment().getName())){
                                        department = departmentService.queryByName(employee.getDepartment().getName());
                                    }
                                    if(department==null){
                                        department = new Department();
                                    }
                                    if(!"".equals(employee.getPosition().getName())){
                                        position = positionService.queryByName(employee.getPosition().getName());
                                    }
                                    if(position==null){
                                        position = new Position();
                                    }

                                    if(department.getId()==null){
                                        department.setId(0);
                                    }

                                    if(position.getId()==null){
                                        position.setId(0);
                                    }

                                    employee.setDepartment(department);
                                    employee.setPosition(position);

                                    int i = employeeService.insert(employee);

                                }
                            }

                            request.setAttribute("inputMessage","导入成功，请去员工主页核对并完善信息！");
                        }else{

                            request.setAttribute("inputMessage","数据导入失败，请使用模板导入！");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        request.setAttribute("inputMessage","部分或数据导入失败，请使用模板导入！");
                    }

                }

            }
//            ajaxResult.setSuccess(true);
        } catch (Exception e) {
//            e.printStackTrace();
//            ajaxResult.setSuccess(false);
//            System.out.println("导入数据失败!!");
            request.setAttribute("inputMessage","数据导入失败，请使用模板导入！");
        }finally {
            request.getRequestDispatcher("/WEB-INF/jsp/employee/input.jsp").forward(request,response);
        }

    }


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try{
            //获取所有的部门信息
            List<Department> departmentList = departmentService.queryAll();

            //获取所有的职位信息

            List<Position> positionList = positionService.queryAll();

            request.setAttribute("departmentList",departmentList);

            request.setAttribute("positionList",positionList);

        }catch (Exception e){
        }finally {

            request.getRequestDispatcher("/WEB-INF/jsp/employee/index.jsp").forward(request,response);
        }



    }


    public void information(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AJAXResult result = new AJAXResult();
        String id =  request.getParameter("id");
        try{
            if(id==null||"".equals(id)){
                result.setSuccess(false);
            }else{

                Integer eid = Integer.parseInt(id);
                Information information = employeeService.quaryInformationByEmployeeId(eid);
                if(information==null){
                    result.setSuccess(false);
                }else{
                    result.setData(information);
                    result.setSuccess(true);
                }
            }
        }catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }



    public void infoUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String idCard = request.getParameter("idCard");
        String phone = request.getParameter("phone");
        String sex = request.getParameter("sex");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        String ismarried = request.getParameter("ismarried");
        String email = request.getParameter("email");
        String edu = request.getParameter("edu");
        String date = request.getParameter("date");
        String createTime = String.valueOf(System.currentTimeMillis());
        String image = request.getParameter("image");



        AJAXResult result = new AJAXResult();
        if(name==null||"".equals(name)||id==null||"".equals(id)){
            result.setSuccess(false);
        }else{
            try {

                Integer employeeId = Integer.parseInt(id);
                Information information = new Information(name,idCard,phone,Integer.parseInt(sex),address,Integer.parseInt(status)
                        ,Integer.parseInt(ismarried),image,email,edu,createTime,employeeId,date);
                Map<String,Object> map = new HashMap<>();
                map.put("eid",id);
                map.put("information",information);
                Information info = employeeService.quaryInformationByEmployeeId(employeeId);
                if(info==null){
                    employeeService.insertInfo(information);
                }else{
                    employeeService.InfoUpdate(map);
                }
                result.setSuccess(true);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }

        response.getWriter().write(JSON.toJSONString(result));




    }


    public void input(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/employee/input.jsp").forward(request,response);
    }


    public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {


        AJAXResult result = new AJAXResult();
        try{
            String name = request.getParameter("name");
            String departmentid = request.getParameter("departmentid");
            String positionid = request.getParameter("positionid");
            long createTime  = System.currentTimeMillis();
            if(name==null||"".equals(name)){
                result.setSuccess(false);
            }else{
                Integer did = Integer.parseInt(departmentid);
                Integer pid;
                if(positionid==null||"".equals(positionid)){
                    pid = 0;
                }else{
                    pid = Integer.parseInt(positionid);
                }
                Department d = new Department();
                d.setId(did);
                Position position = new Position();
                position.setId(pid);
                Employee employee = new Employee();
                employee.setName(name);
                employee.setCreateTime(String.valueOf(createTime));
                employee.setDepartment(d);
                employee.setPosition(position);
                int tag = employeeService.insert(employee);
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
        AJAXResult result = new AJAXResult();
        try {
            //开始的页数
            Integer pageno = Integer.parseInt(request.getParameter("pageno"));
            //大小
            Integer pagesize = Integer.parseInt(request.getParameter("pagesize"));
            //模糊查询的内容
            String queryText = request.getParameter("queryText");

            //分类查询
            String depid = request.getParameter("departmentid");

            String posid = request.getParameter("positionid");
            Integer departmentid = 0;
            Integer positionid = 0;

            if(null!=depid&&!"".equals(depid)){
                departmentid = Integer.parseInt(depid);
            }
            if(null!=posid&&!"".equals(posid)){
                positionid = Integer.parseInt(posid);
            }

            //分页查询
            Map<String,Object> map = new HashMap<>();
            map.put("start",(pageno-1)*pagesize);
            map.put("size",pagesize);
            map.put("queryText",queryText);
            map.put("departmentid",departmentid);
            map.put("positionid",positionid);
            List<Employee> employees = employeeService.pageQueryData(map);

            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = employeeService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Employee> employeePage = new Page<>();
            employeePage.setDatas(employees);
            employeePage.setTotalno(totalno);
            employeePage.setPageno(pageno);
            employeePage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(employeePage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }


    public void select(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String departmentid = request.getParameter("departmentid");
        AJAXResult result = new AJAXResult();
        try {
            if (departmentid != null && !"".equals(departmentid)) {

                List<Position> positions = positionService.queryByDepartmentid(Integer.parseInt(departmentid));
                result.setData(positions);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }



    public void tempFileServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try{


            String filename ="example.xls";

//        String folder = "/upload/";

            //通知浏览器以下载的方式打开

            response.addHeader("Content-Type","application/octet-stream");

            response.addHeader("Content-Disposition","attachment;filename="+filename);

            //获取文件流
//        InputStream in = getServletContext().getResourceAsStream(folder+filename);

            OutputStream out = response.getOutputStream();

            Map<String,Object> map = new HashMap<>();

            List<Employee> list = new ArrayList<>();
            Department department = new Department();
            department.setName("人事部");

            Position position = new Position();
            position.setName("人事部主任");
            Employee employee = new Employee(1,"张三",department,position);
            list.add(employee);
            map.put("employees",list);

            ExcelUtils.JXLExport.export(out,map);

//        byte[] buffer = new byte[1024];
//
//        int len;
//
//        while((len = in.read(buffer))!=-1){
//            out.write(buffer,0,len);
//        }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = new AJAXResult();
        try{
            String name = request.getParameter("name");
            String departmentid = request.getParameter("departmentid");
            String positionid = request.getParameter("positionid");
            Integer id = Integer.parseInt(request.getParameter("id"));
            if(id==null){
                result.setSuccess(false);
            }else{
                Department department = new Department();
                if(departmentid!=null){

                    department.setId(Integer.parseInt(departmentid));
                }else{
                    department.setId(0);
                }
                Position p =new Position();
                if(positionid!=null&&!"".equals(positionid)){
                    p.setId(Integer.parseInt(positionid));
                }else{
                    p.setId(0);
                }
                Employee employee = new Employee(id,name,department,p);
                int tag = employeeService.update(employee);
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
