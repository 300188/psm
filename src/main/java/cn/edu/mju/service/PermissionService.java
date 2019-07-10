package cn.edu.mju.service;

import cn.edu.mju.entity.Permission;
import cn.edu.mju.entity.User;

import java.util.List;

public interface PermissionService extends BaseService<Permission> {


    List<Permission> queryAll();

    List<Integer> queryPermissionidsByRoleid(String roleid);

    List<Permission> queryPermissionsByUser(User user);

    void insertPermission(Permission permission);

    Permission queryById(Integer permissionid);

    void deletePermission(Integer id);

    void updatePermission(Permission permission);
}
