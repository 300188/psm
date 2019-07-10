package cn.edu.mju.dao;

import cn.edu.mju.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleDao extends BaseDao<Role> {
    List<Role> queryAll();

    List<Role> pageQueryData(Map<String,Object> map);

    int pageQueryCount(Map<String,Object> map);

    void insertRolePermission(Map<String,Object> paramMap);

    int deleteRoleById(Integer id);

    int insertRole(Role role);

    int update(Role role);

    int deleteRoles(Map<String,Object> map);

    Role queryById(Integer id);

    List<Role> checkRole(Role role);

    Role getRoleByName(String name);

    int deleteRolePermissions(Map<String, Object> paramMap);
}
