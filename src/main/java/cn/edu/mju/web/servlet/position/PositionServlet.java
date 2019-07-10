package cn.edu.mju.web.servlet.position;

import cn.edu.mju.dto.AJAXResult;
import cn.edu.mju.dto.Page;
import cn.edu.mju.entity.Position;
import cn.edu.mju.service.PositionService;
import cn.edu.mju.service.serviceImpl.PositionServiceImpl;
import cn.edu.mju.web.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/position/*")
public class PositionServlet extends BaseServlet {

    private PositionService positionService = new PositionServiceImpl();


    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/WEB-INF/jsp/position/add.jsp").forward(request,response);
    }



    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = null;

        try{
            //拿到要删除的用户ID
            String id = request.getParameter("id");
            if(!"".equals(id)&&id!=null){
                Integer positionid = Integer.parseInt(id);

                int tag = positionService.deleteById(positionid);
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
        String[] positionids = request.getParameterValues("positionid");

        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("positions", positionids);
            int tag = positionService.deletePositions(map);
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

    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id= request.getParameter("id");
        if(id!=null&&!"".equals(id)){
            try{
                Integer positionid = Integer.parseInt(id);
                Position position = positionService.queryById(positionid);
                if(position!=null){
                    request.getSession().setAttribute("position",position);
                    request.getRequestDispatcher("/WEB-INF/jsp/position/edit.jsp").forward(request,response);
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


    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher("/WEB-INF/jsp/position/index.jsp").forward(request,response);
    }


    public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AJAXResult result = new AJAXResult();

        try{

            String name = request.getParameter("name");
            long createTime  = System.currentTimeMillis();
            Position position = positionService.queryByName(name);
            if(position!=null){
                result.setSuccess(false);
            }else{
                position = new Position(name,String.valueOf(createTime));
                int tag = positionService.insert(position);
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
            List<Position> positions = positionService.pageQueryData(map);
            //总页数
            int totalno = 0;
            //数据总条数
            int totalsize = positionService.pageQueryCount(map);
            if(totalsize % pagesize ==0) {
                totalno = totalsize / pagesize;
            }else {
                totalno = totalsize / pagesize +1;
            }
            //分页对象
            Page<Position> positionPage = new Page<>();
            positionPage.setDatas(positions);
            positionPage.setTotalno(totalno);
            positionPage.setPageno(pageno);
            positionPage.setTotalsize(totalsize);
            result.setSuccess(true);
            result.setData(positionPage);
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
                Position position = positionService.queryByName(name);
                if(position!=null){
                    result.setSuccess(false);
                }else{
                    position = new Position(id,name,String.valueOf(createTime));
                    int tag = positionService.update(position);
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
