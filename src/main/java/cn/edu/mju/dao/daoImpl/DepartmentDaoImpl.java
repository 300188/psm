package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.DepartmentDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Department;
import cn.edu.mju.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

    @Override
    public List<Department> queryAll() {

        DaoMap dm = new DaoMap();
        String sql = "select * from t_department";
        Integer number = 4;
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);

        //封装department 返回结果为List
        return this.makeDepartment(results);
    }

    //根据id删除部门
    @Override
    public void delete(Integer id) {
        if(null==id){
            return ;
        }
        String sql = "delete from t_department where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        this.delete(dm);
    }

    //增加一个部门
    @Override
    public void insert(Department department) {
        if(null==department){
            return ;
        }
        String sql = "insert into t_department (name,pid) values(?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(department.getName());
        args.add(department.getPid());
        dm.setArgs(args);
        this.insert(dm);

    }

    //根据id查找部门
    @Override
    public Department queryById(Integer departmentid) {
        String sql = "select * from t_department where id=?";
        DaoMap dm = new DaoMap();
        Integer number = 4;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(departmentid);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);
        List<Department> list = this.makeDepartment(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }


    //更新部门信息
    @Override
    public void update(Department department) {
        if(null==department){
            return ;
        }
        String sql = "update t_department set name=? where id=?";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(department.getName());
        args.add(department.getId());
        dm.setArgs(args);
        this.update(dm);
    }

    //根据部门id获取部门职位id
    @Override
    public List<Integer> queryPositionidsByDepartmentid(Integer id) {
        if(id==null){
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        String sql = "select positionid from t_department_position where departmentid=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        Integer number = 1;
        dm.setNumber(number);
        List<Object> results = this.query(dm);
        for (Object result : results) {

            List<Object> values = (List<Object>) result;
            Integer positionid = (Integer) values.get(0);
            list.add(positionid);

        }

        return list;
    }

    //给部门重新设置职位
    @Override
    public void insertDepartmentPositions(Map<String, Object> paramMap) {
        Integer departmentid = (Integer) paramMap.get("departmentid");
        String[] pids = (String[]) paramMap.get("positionids");
        String sql = "insert into t_department_position (departmentid, positionid) values(?,?)";
        for (int i = 1; i < pids.length; i++) {
            sql = sql + ",(?,?)";
        }
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();

        for(int i = 0;i<pids.length;i++){

            args.add(departmentid);
            args.add(Integer.parseInt(pids[i]));
        }

        dm.setArgs(args);
        this.insert(dm);


    }


    //删除部门中的某些职位
    @Override
    public void deleteDepartmentPositions(Map<String, Object> paramMap) {
        Integer id= (Integer) paramMap.get("departmentid");
        String sql = "delete from t_department_position where departmentid=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);

        this.delete(dm);
    }

    @Override
    public Department queryByName(String name) {
        String sql = "select * from t_department where name=?";
        DaoMap dm = new DaoMap();
        Integer number = 4;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(name);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Department> list = this.makeDepartment(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }


    //封装Department
    private List<Department> makeDepartment(List<Object> results){

        List<Department> list = new ArrayList<>();

        Department department = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String dbname = (String) values.get(1);
            Integer state = (Integer) values.get(2);
            Integer pid = (Integer) values.get(3);

            department = new Department(id,dbname,state,pid);
            list.add(department);
        }

        return list;

    }


}
