package cn.edu.mju.dao;

import cn.edu.mju.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentDao extends BaseDao<Department> {
    List<Department> queryAll();

    void delete(Integer id);

    void insert(Department department);

    Department queryById(Integer departmentid);

    void update(Department department);

    List<Integer> queryPositionidsByDepartmentid(Integer id);

    void insertDepartmentPositions(Map<String, Object> paramMap);

    void deleteDepartmentPositions(Map<String, Object> paramMap);

    Department queryByName(String name);
}
