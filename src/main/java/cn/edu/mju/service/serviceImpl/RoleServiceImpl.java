package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.RoleDao;
import cn.edu.mju.dao.daoImpl.RoleDaoImpl;
import cn.edu.mju.entity.Role;
import cn.edu.mju.service.RoleService;

import java.util.List;
import java.util.Map;

public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private RoleDao roleDao = new RoleDaoImpl();

    @Override
    public List<Role> pageQueryData(Map<String, Object> map) {
        return roleDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return roleDao.pageQueryCount(map);
    }

    @Override
    public List<Role> queryAll() {
        return roleDao.queryAll();
    }

    @Override
    public void insertRolePermission(Map<String, Object> paramMap) {
        if(roleDao.deleteRolePermissions(paramMap)>0){
            roleDao.insertRolePermission(paramMap);
        }
    }

    @Override
    public int deleteRoleById(Integer id) {
        return roleDao.deleteRoleById(id);
    }

    @Override
    public int deleteRoles(Map<String, Object> map) {
        return roleDao.deleteRoles(map);
    }

    @Override
    public Role queryById(Integer id) {
        return roleDao.queryById(id);
    }

    @Override
    public int insertRole(Role role) {
        return roleDao.insertRole(role);
    }

    @Override
    public List<Role> checkRole(Role role) {
        return roleDao.checkRole(role);
    }

    @Override
    public int update(Role role) {
       return  roleDao.update(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public int insert(Role role) {
        return roleDao.insertRole(role);
    }
}
