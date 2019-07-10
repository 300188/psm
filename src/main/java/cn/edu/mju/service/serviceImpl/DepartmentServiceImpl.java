package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.DepartmentDao;
import cn.edu.mju.dao.daoImpl.DepartmentDaoImpl;
import cn.edu.mju.entity.Department;
import cn.edu.mju.service.DepartmentService;

import java.util.List;
import java.util.Map;

public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    @Override
    public List<Department> queryAll() {
        return  departmentDao.queryAll();
    }

    @Override
    public void deleteDepartmentById(Integer id) {
        departmentDao.delete(id);
    }

    @Override
    public void insert(Department department) {
        departmentDao.insert(department);
    }

    @Override
    public Department queryById(Integer departmentid) {
        return departmentDao.queryById(departmentid);
    }

    @Override
    public void update(Department department) {
        departmentDao.update(department);
    }

    @Override
    public List<Integer> queryPositionidsByDepartmentid(Integer id) {
        return departmentDao.queryPositionidsByDepartmentid(id);
    }

    @Override
    public void insertDepartmentPositions(Map<String, Object> paramMap) {
        departmentDao.insertDepartmentPositions(paramMap);
    }

    @Override
    public void deleteDepartmentPositions(Map<String, Object> paramMap) {
        departmentDao.deleteDepartmentPositions(paramMap);
    }

    @Override
    public Department queryByName(String name) {
        return departmentDao.queryByName(name);
    }
}
