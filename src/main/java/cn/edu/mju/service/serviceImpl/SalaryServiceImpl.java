package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.SalaryDao;
import cn.edu.mju.dao.daoImpl.SalaryDaoImpl;
import cn.edu.mju.entity.Salary;
import cn.edu.mju.service.SalaryService;

import java.util.List;
import java.util.Map;

public class SalaryServiceImpl extends BaseServiceImpl<Salary> implements SalaryService {

    private SalaryDao salaryDao =  new SalaryDaoImpl();

    @Override
    public int update(Salary salary) {
        return salaryDao.update(salary);
    }

    @Override
    public int insert(Salary salary) {
        return salaryDao.insert(salary);
    }

    @Override
    public Salary queryById(Integer employeeid) {
        return salaryDao.queryById(employeeid);
    }

    @Override
    public int deleteById(Integer id) {
        return salaryDao.deleteById(id);
    }

    @Override
    public int deletes(Map<String, Object> map) {
        return salaryDao.deletes(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return salaryDao.pageQueryCount(map);
    }

    @Override
    public List<Salary> pageQueryData(Map<String, Object> map) {
        return salaryDao.pageQueryData(map);
    }
}
