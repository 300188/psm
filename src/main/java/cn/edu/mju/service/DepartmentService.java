package cn.edu.mju.service;

import cn.edu.mju.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends BaseService<Department> {

    List<Department> queryAll();

    void deleteDepartmentById(Integer id);

    void insert(Department department);

    Department queryById(Integer departmentid);

    void update(Department department);

    List<Integer> queryPositionidsByDepartmentid(Integer id);

    void insertDepartmentPositions(Map<String, Object> paramMap);

    void deleteDepartmentPositions(Map<String, Object> paramMap);

    Department queryByName(String name);
}
