package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.PermissionDao;
import cn.edu.mju.dao.daoImpl.PermissionDaoImpl;
import cn.edu.mju.entity.Permission;
import cn.edu.mju.entity.User;
import cn.edu.mju.service.PermissionService;

import java.util.List;

public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    //查询所有权限
    @Override
    public List<Permission> queryAll() {
        return permissionDao.queryAll();
    }

    //查询角色的所有权限的id
    @Override
    public List<Integer> queryPermissionidsByRoleid(String roleid) {
        return permissionDao.queryPermissionidsByRoleid(roleid);
    }

    @Override
    public List<Permission> queryPermissionsByUser(User user) {
        return permissionDao.queryPermissionsByUser(user);
    }

    @Override
    public void insertPermission(Permission permission) {
        permissionDao.insertPermission(permission);
    }

    @Override
    public Permission queryById(Integer permissionid) {
        return permissionDao.queryById(permissionid);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionDao.deletePermission(id);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }
}
