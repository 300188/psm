package cn.edu.mju.web.servlet;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Contract;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Salary;
import cn.edu.mju.service.ContractService;
import cn.edu.mju.service.EmployeeService;
import cn.edu.mju.service.SalaryService;
import cn.edu.mju.service.serviceImpl.ContractServiceImpl;
import cn.edu.mju.service.serviceImpl.EmployeeServiceImpl;
import cn.edu.mju.service.serviceImpl.SalaryServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//工资
@WebServlet("/salary/*")
public class SalaryServlet extends BaseServlet {


    private SalaryService salaryService = new SalaryServiceImpl();

    private EmployeeService employeeService = new EmployeeServiceImpl();


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/salary/index.jsp").forward(request,response);

    }

    //异步加载合同列表
    public void loadData(HttpServletRequest request,HttpServletResponse response) throws IOException {

        AJAXResult result = new AJAXResult();
        try {
            //开始的页数
            Integer pageno = Integer.parseInt(request.getParameter("pageno"));
            //大小
            Integer pagesize = Integer.parseInt(request.getParameter("pagesize"));
            //模糊查询的内容
            String queryText = request.getParameter("queryText");

            //分页查询
            Map<String,Object> map = new HashMap<>();
            map.put("start",(pageno-1)*pagesize);
            map.put("size",pagesize);
            map.put("queryText",queryText);
            List<Salary> salaries = salaryService.pageQueryData(map);

            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = salaryService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Salary> salaryPage = new Page<>();
            salaryPage.setDatas(salaries);
            salaryPage.setTotalno(totalno);
            salaryPage.setPageno(pageno);
            salaryPage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(salaryPage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));



    }



    //添加
    public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        try{
            if(id!=null&&!"".equals(id)){
                request.getRequestDispatcher("/WEB-INF/jsp/salary/add.jsp").forward(request,response);
            }else{
                response.sendRedirect("/toMainPage");
            }

        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/toMainPage");

        }




    }


    //批量删除
    public void deletes(HttpServletRequest request,HttpServletResponse response) throws IOException {

        AJAXResult result = new AJAXResult();
        String[] salaryids = request.getParameterValues("salaryid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("salaryids", salaryids);
            int tag = salaryService.deletes(map);
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


    //删除一个合同
    public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String salaryid = request.getParameter("id");

        AJAXResult result = new AJAXResult();

        try{
            if(salaryid==null||"".equals(salaryid)){
                result.setSuccess(false);
            }else{

                Integer id = Integer.parseInt(salaryid);

                //根据id删除
                int i = salaryService.deleteById(id);
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

    //修改
    public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String id= request.getParameter("id");
        try{

            if(id!=null&&!"".equals(id)){
                Integer employeeid = Integer.parseInt(id);
                Salary salary = salaryService.queryById(employeeid);
                if(salary!=null){
                    request.getSession().setAttribute("salary",salary);
                    request.getRequestDispatcher("/WEB-INF/jsp/salary/edit.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/toMainPage");
                }

            }else{
                response.sendRedirect("/toMainPage");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/toMainPage");

        }


    }

    public void insert(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String salary = request.getParameter("salary");
        String employeeid = request.getParameter("employeeid");
        AJAXResult result = new AJAXResult();
        try{
            if(employeeid==null){
                result.setSuccess(false);
            }else{

                Employee employee = employeeService.queryById(Integer.parseInt(employeeid));
                if(employee!=null){

                    Salary salary1 = new Salary();
                    salary1.setEmployee(employee);
                    salary1.setSalary(Integer.parseInt(salary));
                    salary1.setEmployeeid(employee.getId());
                    salary1.setName(name);
                    int tag = salaryService.insert(salary1);
                    if(tag>0){
                        result.setSuccess(true);
                    }else{
                        result.setSuccess(false);
                    }
                }


            }
        }catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));


    }


    //重新
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String salary = request.getParameter("salary");
        String employeeid = request.getParameter("employeeid");
        AJAXResult result = new AJAXResult();
        try{
            if(id==null){
                result.setSuccess(false);
            }else{

                Employee employee = employeeService.queryById(Integer.parseInt(employeeid));
                if(employee!=null){


                    Salary salary1 = new Salary();
                    salary1.setSalary(Integer.parseInt(salary));
                    salary1.setId(Integer.parseInt(id));
                    salary1.setEmployee(employee);
                    salary1.setEmployeeid(employee.getId());
                    salary1.setName(name);
                    int tag = salaryService.update(salary1);
                    if(tag>0){
                        result.setSuccess(true);
                    }else{
                        result.setSuccess(false);
                    }
                }


            }
        }catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));



    }





}
