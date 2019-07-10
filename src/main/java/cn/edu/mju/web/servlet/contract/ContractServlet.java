package cn.edu.mju.web.servlet.contract;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Contract;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.service.ContractService;
import cn.edu.mju.service.EmployeeService;
import cn.edu.mju.service.serviceImpl.ContractServiceImpl;
import cn.edu.mju.service.serviceImpl.EmployeeServiceImpl;
import cn.edu.mju.web.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/contract/*")
public class ContractServlet extends BaseServlet {

    private ContractService contractService = new ContractServiceImpl();

    private EmployeeService employeeService = new EmployeeServiceImpl();


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/contract/index.jsp").forward(request,response);

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
            List<Contract> contracts = contractService.pageQueryData(map);

            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = contractService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Contract> contractPage = new Page<>();
            contractPage.setDatas(contracts);
            contractPage.setTotalno(totalno);
            contractPage.setPageno(pageno);
            contractPage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(contractPage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));



    }



    //添加一个合同项
    public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        try{
            if(id!=null&&!"".equals(id)){
                request.getRequestDispatcher("/WEB-INF/jsp/contract/add.jsp").forward(request,response);
            }else{
                response.sendRedirect("/toMainPage");
            }

        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/toMainPage");

        }




    }


    //批量删除合同
    public void deletes(HttpServletRequest request,HttpServletResponse response) throws IOException {

        AJAXResult result = new AJAXResult();
        String[] contractids = request.getParameterValues("contractid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("contractids", contractids);
            int tag = contractService.deleteContracts(map);
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

        String contractid = request.getParameter("id");

        AJAXResult result = new AJAXResult();

        try{
            if(contractid==null||"".equals(contractid)){
                result.setSuccess(false);
            }else{

                Integer id = Integer.parseInt(contractid);

                //根据id删除对应的员工
                int i = contractService.deleteById(id);
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

    //修改合同
    public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String id= request.getParameter("id");
        try{

            if(id!=null&&!"".equals(id)){
                Integer employeeid = Integer.parseInt(id);
                Contract contract = contractService.queryById(employeeid);
                if(contract!=null){
                    request.getSession().setAttribute("contract",contract);
                    request.getRequestDispatcher("/WEB-INF/jsp/contract/edit.jsp").forward(request,response);
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


    //签订合同

    public void insert(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String limit = request.getParameter("limit");
        String employeeid = request.getParameter("employeeid");
        AJAXResult result = new AJAXResult();
        try{
            if(employeeid==null){
                result.setSuccess(false);
            }else{

                Employee employee = employeeService.queryById(Integer.parseInt(employeeid));
                if(employee!=null){

                    Contract contract = new Contract();
                    contract.setEmployee(employee);
                    contract.setLimit(Integer.parseInt(limit));
                    contract.setName(name);
                    contract.setCreateTime(String.valueOf(System.currentTimeMillis()));
                    int tag = contractService.insert(contract);
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


    //重新签订合同
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String limit = request.getParameter("limit");
        String employeeid = request.getParameter("employeeid");
        AJAXResult result = new AJAXResult();
        try{
            if(id==null){
                result.setSuccess(false);
            }else{

                Employee employee = employeeService.queryById(Integer.parseInt(employeeid));
                if(employee!=null){

                    Contract contract = new Contract();
                    contract.setId(Integer.parseInt(id));
                    contract.setEmployee(employee);
                    contract.setLimit(Integer.parseInt(limit));
                    contract.setName(name);
                    contract.setCreateTime(String.valueOf(System.currentTimeMillis()));
                    int tag = contractService.update(contract);
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
