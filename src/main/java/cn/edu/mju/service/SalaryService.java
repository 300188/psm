package cn.edu.mju.service;

import cn.edu.mju.entity.Salary;

import java.util.List;
import java.util.Map;

public interface SalaryService extends BaseService<Salary> {
    int update(Salary salary);

    int insert(Salary salary);

    Salary queryById(Integer employeeid);

    int deleteById(Integer id);

    int deletes(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    List<Salary> pageQueryData(Map<String, Object> map);
}
