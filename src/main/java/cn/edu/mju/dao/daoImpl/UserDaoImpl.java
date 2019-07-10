package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.BaseDao;
import cn.edu.mju.dao.UserDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Role;
import cn.edu.mju.entity.User;
import cn.edu.mju.utils.DBPool;
import cn.edu.mju.utils.DBUtils;
import org.omg.CORBA.Current;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


    //根据登录账户和密码查找登录用户
    @Override
    public User checkLoginUser(String loginAccount, String password) {
        String sql = "select * from t_user where loginAccount=? and password=?";
        DaoMap dm = new DaoMap();
        Integer number = 7;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(loginAccount);
        args.add(password);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<User> list = this.makeUser(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //分页查询数据
    @Override
    public List<User> pageQueryData(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        List<User> list = new ArrayList<>();

            Integer start = (Integer) map.get("start");
            Integer size = (Integer) map.get("size");
            String queryText = (String) map.get("queryText");
            String sql = null;
            if("".equals(queryText)||null==queryText){
                sql = "select * from t_user order by createTime desc limit ? , ?";
            }else{
                sql = "select * from t_user where username like concat('%',?,'%') order by createTime desc limit ?,?";
                args.add(queryText);
            }
            DaoMap dm = new DaoMap();
            Integer number = 7;
            args.add(start);
            args.add(size);
            dm.setArgs(args);
            dm.setSql(sql);
            dm.setNumber(number);
            //查询
            List<Object> results = this.query(dm);

            //封装user 返回结果为List
            return this.makeUser(results);

    }

    //查询总的个数
    @Override
    public int pageQueryCount(Map<String, Object> map) {

        List<Object> args = new ArrayList<>();

        String sql = "";
        if("".equals(map.get("queryText"))||map.get("queryText")==null){
            sql  = "select count(*) from t_user";
        }else {
            sql = "select count(*) from t_user where username like concat('%',?,'%')";
            args.add(map.get("queryText"));
        }

        DaoMap dm = new DaoMap();
        Integer number = 1;
        dm.setSql(sql);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);
        if (results != null && results.size() > 0) {
            List<Object> values = (List<Object>) results.get(0);
            if (values != null && values.size() > 0) {
                return ((Number)values.get(0)).intValue();
            } else {
                return 0;
            }

        }
        return 0;

    }

    //根据id删除用户
    @Override
    public int deleteUserById(Integer userid) {

        if(null==userid){
            return 0;
        }
        String sql = "delete from t_user where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(userid);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }

    //根据id批量删除用户
    @Override
    public int deleteUsers(Map<String, Object> map) {
        int i = 0;
        String[] ids = (String[]) map.get("userids");
        if(null==ids){
            return i;
        }
        String sql = sql = "delete from t_user where id in(?";

        for (int j = 1;j<ids.length;j++) {
            sql = sql+",?";
        }
        sql = sql+")";
        List<Object> args = new ArrayList<>();

        for (int t = 0;t<ids.length;t++) {
            args.add(Integer.parseInt(ids[t]));
        }
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;

    }

    //增加一个登录用户
    @Override
    public int insert(User user) {
        int i = 0;
        if(null==user){
            return i;
        }
        String sql = "insert into t_user(username,loginAccount,password,email,createTime) values(?,?,?,?,?)";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(user.getUsername());
        args.add(user.getLoginAccount());
        args.add(user.getPassword());
        args.add(user.getEmail());
        args.add(user.getCreateTime());
        dm.setArgs(args);
        boolean tag = this.insert(dm);
        return tag?1:0;
    }

    //根据登录名查找用户
    @Override
    public User getUserByLoginAccount(String loginAccount) {
        User user = null;
        String sql = "select * from t_user where loginAccount=?";
        DaoMap dm = new DaoMap();
        Integer number = 7;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(loginAccount);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<User> list = this.makeUser(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //更新用户信息
    @Override
    public int update(User user) {
        if(null==user){
            return 0;
        }
        String sql = "update t_user set username=?,loginAccount=?,password=?,email=?,createTime=? where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(user.getUsername());
        args.add(user.getLoginAccount());
        args.add(user.getPassword());
        args.add(user.getEmail());
        args.add(user.getCreateTime());
        args.add(user.getId());
        dm.setArgs(args);
        boolean tag = this.update(dm);
        return tag?1:0;
    }

    //根据用户id查找用户
    @Override
    public User queryById(Integer id) {
        if(id==null){
            return new User();
        }
        String sql = "select * from t_user where id=?";
        DaoMap dm = new DaoMap();
        Integer number = 7;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<User> list = this.makeUser(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //根据用户id去角色和用户的关系表中查找roleid
    @Override
    public List<Integer> queryRoleidsByUserid(Integer id) {
        if(id==null){
            return new ArrayList<>();
        }
        String sql = "select roleid from t_user_role where userid=?";
        DaoMap dm = new DaoMap();
        Integer number = 1;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setNumber(number);
        dm.setArgs(args);
        List<Integer> list = new ArrayList<>();
        //查询
        List<Object> results = this.query(dm);
        if(results!=null&&results.size()>0){
            for (Object result : results) {
                List<Integer> values = (List<Integer>) result;
                list.add(values.get(0));
            }
        }
        return list;
    }

    //根据用户id 给对应的用户分配角色
    @Override
    public void insertUserRoles(Map<String, Object> paramMap) {
            Integer userid = (Integer) paramMap.get("userid");
            String[] roleids = (String[]) paramMap.get("roleids");
            String sql = "insert into t_user_role (userid,roleid) values(?,?)";
            List<Object> args = new ArrayList<>();
            if (roleids.length > 1) {
                for (int i = 1; i < roleids.length; i++) {
                    sql = sql + ",(?,?)";
                }

                for(int i = 0;i<roleids.length;i++){

                    args.add(userid);
                    args.add(Integer.parseInt(roleids[i]));

                }

            }else{
                args.add(userid);
                args.add(Integer.parseInt(roleids[0]));

            }

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        dm.setArgs(args);
        this.insert(dm);

    }

    @Override
    public void deleteUserRoles(Map<String, Object> paramMap) {
        List<Object> args = new ArrayList<>();

        Integer userid = (Integer) paramMap.get("userid");
        String[] roleids = (String[]) paramMap.get("roleids");
        String sql = "delete from t_user_role where userid = ? and roleid in(?";
        for (int j = 1;j<roleids.length;j++) {
            sql = sql+",?";
        }
        sql = sql+")";
        args.add(userid);
        for (int t = 0;t<roleids.length;t++) {
            args.add(Integer.parseInt(roleids[t]));
        }
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        dm.setArgs(args);
        this.delete(dm);

    }


    private List<User> makeUser(List<Object> results){


        List<User> list = new ArrayList<>();

        User user = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String username = (String) values.get(1);
            String loginAcco = (String) values.get(2);
            String pd = (String) values.get(3);
            String email = (String) values.get(4);
            String createTime = (String) values.get(5);
            Integer state = (Integer) values.get(6);

            user = new User(id,username,loginAcco,pd,email,createTime);
            list.add(user);
        }

        return list;



    }


}
