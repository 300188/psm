package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.UserDao;
import cn.edu.mju.dao.daoImpl.UserDaoImpl;
import cn.edu.mju.entity.User;
import cn.edu.mju.service.UserService;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.List;
import java.util.Map;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService   {

    private UserDao userDao = new UserDaoImpl();


    //检查登录账户是否正确
    @Override
    public User checkLoginUser(String loginAccount, String password) {
        return userDao.checkLoginUser(loginAccount,password);
    }

    @Override
    public List<User> pageQueryData(Map<String, Object> map) {
        return userDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return userDao.pageQueryCount(map);
    }

    @Override
    public int deleteUserById(Integer userid) {
        return userDao.deleteUserById(userid);
    }

    @Override
    public int deleteUsers(Map<String, Object> map) {
        return userDao.deleteUsers(map);
    }

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public User getUserByLoginAccount(String loginAccount) {
        return userDao.getUserByLoginAccount(loginAccount);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public User queryById(Integer id) {
        return userDao.queryById(id);
    }

    @Override
    public void insertUserRoles(Map<String, Object> paramMap) {
        userDao.insertUserRoles(paramMap);
    }

    @Override
    public void deleteUserRoles(Map<String, Object> paramMap) {
        userDao.deleteUserRoles(paramMap);
    }

    @Override
    public List<Integer> queryRoleidsByUserid(Integer id) {
        return userDao.queryRoleidsByUserid(id);
    }
}
