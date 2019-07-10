package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.EmployeeDao;
import cn.edu.mju.dao.SalaryDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Contract;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Salary;
import cn.edu.mju.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalaryDaoImpl extends BaseDaoImpl<Salary> implements SalaryDao {

    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public int update(Salary salary) {
        if(null==salary){
            return 0;
        }
        String sql = "update t_salary set name=?,salary=? where id=?";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(salary.getName());
        args.add(salary.getSalary());
        args.add(salary.getId());
        dm.setArgs(args);
        boolean tag = this.update(dm);
        return tag?1:0;
    }

    private List<Salary> makeSalary(List<Object> results) {

        List<Salary> list = new ArrayList<>();

        Salary salary = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String name = (String) values.get(1);
            Integer salarytext = (Integer) values.get(2);
            Integer employeeid = (Integer) values.get(3);
            Employee employee = null;
            if(employeeid!=null){
                employee = employeeDao.queryById(employeeid);
            }

            if(employee==null){
                employee = new Employee();
            }

            salary = new Salary();
            salary.setId(id);
            salary.setName(name);
            salary.setSalary(salarytext);
            salary.setEmployee(employee);
            salary.setEmployeeid(employeeid);
            list.add(salary);
        }

        return list;
    }

    @Override
    public int insert(Salary salary) {
        if(null==salary){
            return 0;
        }
        String sql = "insert into t_salary(name,salary,employeeid) values (?,?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();

        args.add(salary.getName());
        args.add(salary.getSalary());
        args.add(salary.getEmployeeid());
        dm.setArgs(args);
        return this.insert(dm)?1:0;
    }

    @Override
    public Salary queryById(Integer employeeid) {
        if(employeeid==null){
            return null;
        }
        Contract contract = null;
        String sql = "select * from t_salary where id=?";

        DaoMap dm = new DaoMap();
        Integer number = 4;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(employeeid);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Salary> list = this.makeSalary(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int deleteById(Integer id) {
        int i = 0;
        if(null==id){
            return i;
        }
        String sql = "delete from t_salary where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }

    @Override
    public int deletes(Map<String, Object> map) {
        int i = 0;
        String[] ids = (String[]) map.get("salaryids");
        if(null==ids){
            return i;
        }
        String sql = sql = "delete from t_salary where id in(?";

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
    public int pageQueryCount(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        String sql = "";
        if("".equals(map.get("queryText"))||map.get("queryText")==null){
            sql  = "select count(*) from t_salary";
        }else {
            sql = "select count(*) from t_salary where name like concat('%',?,'%')";
            args.add(map.get("queryText"));
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
    public List<Salary> pageQueryData(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        List<User> list = new ArrayList<>();

        Integer start = (Integer) map.get("start");
        Integer size = (Integer) map.get("size");
        String queryText = (String) map.get("queryText");
        String sql = null;
        if("".equals(queryText)||null==queryText){
            sql = "select * from t_salary order by salary desc limit ? , ?";
        }else{
            sql = "select * from t_salary where name like concat('%',?,'%') order by salary desc limit ?,?";
            args.add(queryText);
        }
        DaoMap dm = new DaoMap();
        Integer number = 4;
        args.add(start);
        args.add(size);
        dm.setArgs(args);
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);

        //封装user 返回结果为List
        return this.makeSalary(results);
    }
}
