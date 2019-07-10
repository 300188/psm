package cn.edu.mju.web.servlet.record;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Contract;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Record;
import cn.edu.mju.service.EmployeeService;
import cn.edu.mju.service.RecordService;
import cn.edu.mju.service.serviceImpl.EmployeeServiceImpl;
import cn.edu.mju.service.serviceImpl.RecordServiceImpl;
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

@WebServlet("/record/*")
public class RecordServlet extends BaseServlet {

    private RecordService recordService = new RecordServiceImpl();

    private EmployeeService employeeService = new EmployeeServiceImpl();


    //index.jsp
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/record/index.jsp").forward(request,response);

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
            List<Record> records = recordService.pageQueryData(map);

            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = recordService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Record> recordPage = new Page<>();
            recordPage.setDatas(records);
            recordPage.setTotalno(totalno);
            recordPage.setPageno(pageno);
            recordPage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(recordPage);
        }catch(Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }

    public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        try{
            if(id!=null&&!"".equals(id)){
                request.getRequestDispatcher("/WEB-INF/jsp/record/add.jsp").forward(request,response);
            }else{
                response.sendRedirect("/toMainPage");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/toMainPage");

        }

    }



    public void deletes(HttpServletRequest request,HttpServletResponse response) throws IOException {

        AJAXResult result = new AJAXResult();
        String[] recordids = request.getParameterValues("recordid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("recordids", recordids);
            int tag = recordService.deletes(map);
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

        String recordid = request.getParameter("id");

        AJAXResult result = new AJAXResult();

        try{
            if(recordid==null||"".equals(recordid)){
                result.setSuccess(false);
            }else{

                Integer id = Integer.parseInt(recordid);

                //根据id删除对应的员工
                int i = recordService.deleteById(id);
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
                Integer recordid = Integer.parseInt(id);
                Record record = recordService.queryById(recordid);
                if(record!=null){
                    request.getSession().setAttribute("record",record);
                    request.getRequestDispatcher("/WEB-INF/jsp/record/edit.jsp").forward(request,response);
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

        String text = request.getParameter("text");
        String employeeid = request.getParameter("employeeid");
        AJAXResult result = new AJAXResult();
        try{
            if(employeeid==null){
                result.setSuccess(false);
            }else{

                Employee employee = employeeService.queryById(Integer.parseInt(employeeid));
                if(employee!=null){

                    Record record = new Record();
                    record.setText(text);
                    record.setEmployee(employee);
                    record.setCreateTime(String.valueOf(System.currentTimeMillis()));
                    int tag = recordService.insert(record);
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
        String text = request.getParameter("text");
        AJAXResult result = new AJAXResult();
        try{
            if(id==null){
                result.setSuccess(false);
            }else{
                    Record record = new Record();
                    record.setText(text);
                    record.setId(Integer.parseInt(id));
                    record.setCreateTime(String.valueOf(System.currentTimeMillis()));
                    int tag = recordService.update(record);
                    if(tag>0){
                        result.setSuccess(true);
                    }else{
                        result.setSuccess(false);
                    }
            }

        } catch (Exception e){
            result.setSuccess(false);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }






}
