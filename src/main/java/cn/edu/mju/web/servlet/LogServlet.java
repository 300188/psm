package cn.edu.mju.web.servlet;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Log;
import cn.edu.mju.entity.Record;
import cn.edu.mju.service.EmployeeService;
import cn.edu.mju.service.LogService;
import cn.edu.mju.service.serviceImpl.EmployeeServiceImpl;
import cn.edu.mju.service.serviceImpl.LogServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//日志
@WebServlet("/log/*")
public class LogServlet extends BaseServlet {

    private LogService logService = new LogServiceImpl();

    private EmployeeService employeeService = new EmployeeServiceImpl();


    //index.jsp
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/log/index.jsp").forward(request,response);

    }


    public void loadData(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
            List<Log> logs = logService.pageQueryData(map);

            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = logService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Log> logPage = new Page<>();
            logPage.setDatas(logs);
            logPage.setTotalno(totalno);
            logPage.setPageno(pageno);
            logPage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(logPage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }



    public void deletes(HttpServletRequest request,HttpServletResponse response) throws IOException {

        AJAXResult result = new AJAXResult();
        String[] logids = request.getParameterValues("logid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("logids", logids);
            int tag = logService.deletes(map);
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


    //删除
    public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String logid = request.getParameter("id");

        AJAXResult result = new AJAXResult();

        try{
            if(logid==null||"".equals(logid)){
                result.setSuccess(false);
            }else{

                Integer id = Integer.parseInt(logid);

                //根据id删除
                int i = logService.deleteById(id);
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



}
