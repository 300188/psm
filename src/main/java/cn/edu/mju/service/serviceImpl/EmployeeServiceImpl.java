package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.EmployeeDao;
import cn.edu.mju.dao.daoImpl.EmployeeDaoImpl;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Information;
import cn.edu.mju.service.EmployeeService;

import java.util.List;
import java.util.Map;

/**
 * 员工的service类
 */
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public List<Employee> pageQueryData(Map<String, Object> map) {
        return employeeDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return employeeDao.pageQueryCount(map);
    }

    @Override
    public Information quaryInformationByEmployeeId(Integer eid) {
        return employeeDao.quaryInformationByEmployeeId(eid);
    }

    @Override
    public int deleteById(Integer id) {
        return employeeDao.deleteById(id);
    }


    @Override
    public int deleteEmployees(Map<String, Object> map) {
        return employeeDao.deleteEmployees(map);
    }

    @Override
    public Information queryInformationByIdCard(String idCard) {
        return employeeDao.queryInformationByIdCard(idCard);
    }

    @Override
    public int insert(Employee employee) {
        return employeeDao.insert(employee);
    }

    @Override
    public Employee queryById(Integer employeeid) {
        return employeeDao.queryById(employeeid);
    }

    @Override
    public int update(Employee employee) {
        return employeeDao.update(employee);
    }

    @Override
    public void InfoUpdate(Map<String,Object> map) {
        employeeDao.InfoUpdate(map);
    }


    @Override
    public void insertInfo(Information information) {
        employeeDao.insertInfo(information);
    }

    @Override
    public List<Employee> queryAll() {
        return employeeDao.queryAll();
    }

    @Override
    public List<Information> queryAllInformation() {
        return employeeDao.queryAllInformation();
    }
}
