package cn.edu.mju.dao;

import cn.edu.mju.entity.Permission;
import cn.edu.mju.entity.User;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {


    List<Integer> queryPermissionidsByRoleid(String roleid);

    List<Permission> queryAll();

    List<Permission> queryPermissionsByUser(User user);

    void insertPermission(Permission permission);

    Permission queryById(Integer permissionid);

    void deletePermission(Integer id);

    void updatePermission(Permission permission);
}
