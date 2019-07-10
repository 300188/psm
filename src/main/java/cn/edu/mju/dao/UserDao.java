package cn.edu.mju.dao;

import cn.edu.mju.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao<User> {
    User checkLoginUser(String loginAccount, String password);

    List<User> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    int deleteUserById(Integer userid);

    int deleteUsers(Map<String, Object> map);

    int insert(User user);

    User getUserByLoginAccount(String loginAccount);

    int update(User user);

    User queryById(Integer id);

    List<Integer> queryRoleidsByUserid(Integer id);

    void insertUserRoles(Map<String, Object> paramMap);

    void deleteUserRoles(Map<String, Object> paramMap);
}
