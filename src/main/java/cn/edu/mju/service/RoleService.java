package cn.edu.mju.service;

import cn.edu.mju.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {
    List<Role> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    List<Role> queryAll();

    void insertRolePermission(Map<String, Object> paramMap);

    int deleteRoleById(Integer id);

    int deleteRoles(Map<String, Object> map);

    Role queryById(Integer id);

    int insertRole(Role role);

    List<Role> checkRole(Role role);

    int update(Role role);

    Role getRoleByName(String name);

    int insert(Role role);
}
