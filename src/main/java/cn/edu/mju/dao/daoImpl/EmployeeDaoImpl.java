package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.DepartmentDao;
import cn.edu.mju.dao.EmployeeDao;
import cn.edu.mju.dao.PositionDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Department;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Information;
import cn.edu.mju.entity.Position;
import cn.edu.mju.service.DepartmentService;
import cn.edu.mju.service.serviceImpl.DepartmentServiceImpl;
import cn.edu.mju.utils.DBUtils;
import org.omg.CORBA.PERSIST_STORE;
import sun.dc.pr.PRError;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {


    private DepartmentDao departmentDao = new DepartmentDaoImpl();
    private PositionDao positionDao = new PositionDaoImpl();

    //分页查询数据
    @Override
    public List<Employee>  pageQueryData(Map<String, Object> map) {
            List<Object> args = new ArrayList<>();

            Integer start = (Integer) map.get("start");
            Integer size = (Integer) map.get("size");
            String queryText = (String) map.get("queryText");
            Integer posid = (Integer) map.get("positionid");
            Integer depid = (Integer) map.get("departmentid");
            String sql = null;
            if("".equals(queryText)||null==queryText){

                sql = "select * from t_employee ";
                if(posid>0&&null!=posid){
                    sql = sql+" where positionid=?";
                    args.add(posid);
                    if(depid>0&&null!=depid){
                        sql=sql+" and departmentid=?";
                        args.add(depid);
                    }
                }else{
                    if(depid>0&&null!=depid){
                        sql=sql+" where departmentid=?";
                        args.add(depid);
                    }
                }
                sql = sql+" order by createTime desc limit ? , ?";

            }else{
                args.add(queryText);
                sql = "select * from t_employee where name like concat('%',?,'%') ";
                if(posid>0&&null!=posid){
                    sql = sql+" and positionid=?";
                    args.add(posid);
                    if(depid>0&&null!=depid){
                        sql=sql+" and departmentid=?";
                        args.add(depid);
                    }
                }else{
                    if(depid>0&&null!=depid){
                        sql=sql+" and departmentid=?";
                        args.add(depid);
                    }
                }
                sql = sql+" order by createTime desc limit ? , ?";
            }
            args.add(start);
            args.add(size);
            DaoMap daoMap = new DaoMap();
            daoMap.setNumber(6);
            daoMap.setArgs(args);
            daoMap.setSql(sql);
            List<Object> results = this.query(daoMap);

            return  this.makeEmployee(results);

    }


    //查询总的个数
    @Override
    public int pageQueryCount(Map<String, Object> map) {

        List<Object> args = new ArrayList<>();

        String queryText = (String) map.get("queryText");
        Integer posid = (Integer) map.get("positionid");
        Integer depid = (Integer) map.get("departmentid");
        String sql = null;
        if ("".equals(queryText) || null == queryText) {
            sql = "select count(*) from t_employee ";
            if (posid > 0 && null != posid) {
                sql = sql + " where positionid=?";
                args.add(posid);
                if (depid > 0 && null != depid) {
                    args.add(depid);
                    sql = sql + " and departmentid=?";
                }
            } else {
                if (depid > 0 && null != depid) {
                    args.add(depid);
                    sql = sql + " where departmentid=?";
                }
            }

        } else {
            args.add(queryText);
            sql = "select count(*)  from t_employee where name like concat('%',?,'%') ";
            if (posid > 0 && null != posid) {
                args.add(posid);
                sql = sql + " and positionid=?";
                if (depid > 0 && null != depid) {
                    args.add(depid);
                    sql = sql + " and departmentid=?";
                }

            } else {
                if (depid > 0 && null != depid) {
                    args.add(depid);
                    sql = sql + " and departmentid=?";
                }
            }
        }

        DaoMap dm = new DaoMap();
        Integer number = 1;
        dm.setSql(sql);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);
        if (results != null && results.size() > 0) {
            List<Object> values = (List<Object>) results.get(0);
            if (values != null && values.size() > 0) {
                return ((Number)values.get(0)).intValue();
            } else {
                return 0;
            }

        }
        return 0;

    }



    @Override
    public Information quaryInformationByEmployeeId(Integer eid) {
        String sql = "select * from t_information where eid=?";

        DaoMap dm = new DaoMap();
        Integer number = 14;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(eid);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Information> list = this.makeInformation(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }




    //根据id删除员工
    @Override
    public int deleteById(Integer id) {
        int i = 0;
        if(null==id){
            return i;
        }
        String sql = "delete from t_employee where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }


    //批量删除员工
    @Override
    public int deleteEmployees(Map<String, Object> map) {
        int i = 0;
        String[] ids = (String[]) map.get("employeeids");
        if(null==ids){
            return i;
        }
        String sql = sql = "delete from t_employee where id in(?";

        for (int j = 1;j<ids.length;j++) {
            sql = sql+",?";
        }
        sql = sql+")";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        for (int t = 0;t<ids.length;t++) {
            args.add(Integer.parseInt(ids[t]));
        }
        dm.setArgs(args);

        boolean tag = this.delete(dm);
        return tag?1:0;
    }

    @Override
    public Information queryInformationByIdCard(String idCard) {
        String sql = "select * from t_information where idCard=?";
        DaoMap dm = new DaoMap();
        Integer number = 14;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(idCard);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Information> list = this.makeInformation(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }

    //增加员工
    @Override
    public int insert(Employee employee) {
        int i = 0;
        if(null==employee){
            return i;
        }
        String sql = "insert into t_employee(name,createTime,departmentid,positionid) values(?,?,?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(employee.getName());
        args.add(employee.getCreateTime());
        args.add(employee.getDepartment().getId());
        args.add(employee.getPosition().getId());
        dm.setArgs(args);

        boolean tag = this.insert(dm);

        return tag?1:0;
    }


    @Override
    public Employee queryById(Integer employeeid) {
        if(employeeid==null){
            return null;
        }
        Employee employee = null;
        String sql = "select * from t_employee where id=?";

        DaoMap dm = new DaoMap();
        Integer number = 6;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(employeeid);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Employee> list = this.makeEmployee(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }


    @Override
    public int update(Employee employee) {
        if(null==employee){
            return 0;
        }
        String sql = "update t_employee set name=?,departmentid=?,positionid=? where id=?";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(employee.getName());
        args.add(employee.getDepartment().getId());
        args.add(employee.getPosition().getId());
        args.add(employee.getId());
        dm.setArgs(args);
        boolean tag = this.update(dm);
        return tag?1:0;
    }

    @Override
    public void InfoUpdate(Map<String,Object> map) {
        if(null==map){
            return ;
        }
        Information information = (Information) map.get("information");
        String eid = (String) map.get("eid");

        String sql = "update t_information set name=?,idCard=?,phone=?,sex=?,address=?,status=?,ismarried=?,image=?,email=?,edu=?,date=? where eid=?";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(information.getName());
        args.add(information.getIdCard());
        args.add(information.getPhone());
        args.add(information.getSex());
        args.add(information.getAddress());
        args.add(information.getStatus());
        args.add(information.getIsmarried());
        args.add(information.getImage());
        args.add(information.getEmail());
        args.add(information.getEdu());
        args.add(information.getDate());
        args.add(information.getEid());
        dm.setArgs(args);
        boolean tag = this.update(dm);
    }


    @Override
    public void insertInfo(Information information) {
        if(null==information){
            return ;
        }
        String sql = "insert into t_information(name,idCard,phone,sex,address,status,ismarried,image,email,edu,createTime,eid,date) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(information.getName());
        args.add(information.getIdCard());
        args.add(information.getPhone());
        args.add(information.getSex());
        args.add(information.getAddress());
        args.add(information.getStatus());
        args.add(information.getIsmarried());
        args.add(information.getImage());
        args.add(information.getEmail());
        args.add(information.getEdu());
        args.add(information.getCreateTime());
        args.add(information.getEid());
        args.add(information.getDate());
        dm.setArgs(args);
        this.insert(dm);
    }


    @Override
    public List<Employee> queryAll() {
        String sql = "select * from t_employee";
        DaoMap dm = new DaoMap();
        Integer number = 6;
        dm.setSql(sql);
        dm.setNumber(number);

        //查询
        List<Object> results = this.query(dm);

        //封装department 返回结果为List
        return this.makeEmployee(results);
    }

    @Override
    public List<Information> queryAllInformation() {
        String sql = "select * from t_information";
        DaoMap dm = new DaoMap();
        Integer number = 14;
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);

        List<Information> list = this.makeInformation(results);

        return list;
    }

    //封装information
    private List<Information> makeInformation(List<Object> results){

        List<Information> list = new ArrayList<>();

        Information information = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String name = (String) values.get(1);
            String idCard= (String) values.get(2);
            String phone= (String) values.get(3);
            Integer sex= (Integer) values.get(4);
            String address= (String) values.get(5);
            Integer status= (Integer) values.get(6);
            Integer ismarried= (Integer) values.get(7);
            String image= (String) values.get(8);
            String email= (String) values.get(9);
            String edu= (String) values.get(10);
            String createTime = (String) values.get(11);
            Integer employeeid = (Integer) values.get(12);
            String date = (String) values.get(13);
            information = new Information(id,name,idCard,phone,sex,address,status,ismarried,image,email,edu,createTime,employeeid,date);
            list.add(information);

        }

        return list;



    }

    //封装员工信息

    private List<Employee> makeEmployee(List<Object> results) {

        List<Employee> list = new ArrayList<>();

        Employee employee = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String name = (String) values.get(1);
            String createTime = (String) values.get(2);
            //根据id查部门
            Integer departmentid = (Integer) values.get(3);

            Department department = departmentDao.queryById(departmentid);
            if(department==null){
                department = new Department();
            }

            //根据id查职位
            Integer positionid = (Integer) values.get(4);

            Position position = positionDao.queryById(positionid);
            if(position==null){
                position = new Position();
            }

            Integer state = (Integer) values.get(5);

            employee = new Employee(id,name,createTime,department,position,state);

            list.add(employee);

        }

        return list;
    }


}
