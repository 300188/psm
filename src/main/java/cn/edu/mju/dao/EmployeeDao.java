package cn.edu.mju.dao;

import cn.edu.mju.entity.Department;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Information;
import cn.edu.mju.entity.Position;

import java.util.List;
import java.util.Map;

public interface EmployeeDao extends BaseDao<Employee> {
    List<Employee> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    Information quaryInformationByEmployeeId(Integer eid);

    int deleteById(Integer id);

    int deleteEmployees(Map<String, Object> map);

    Information queryInformationByIdCard(String idCard);

    int insert(Employee employee);

    Employee queryById(Integer employeeid);

    int update(Employee employee);

    void InfoUpdate(Map<String,Object> map);

    void insertInfo(Information information);

    List<Employee> queryAll();

    List<Information> queryAllInformation();

}
