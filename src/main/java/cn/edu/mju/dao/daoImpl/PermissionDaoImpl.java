package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.PermissionDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Permission;
import cn.edu.mju.entity.Position;
import cn.edu.mju.entity.User;
import cn.edu.mju.service.PermissionService;
import cn.edu.mju.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements PermissionDao {



    @Override
    public List<Integer> queryPermissionidsByRoleid(String roleid) {

        if(roleid==null||"".equals(roleid)){
            return new ArrayList<>();
        }
        String sql = "select pid from t_role_permission where roleid=?";

        DaoMap dm = new DaoMap();
        Integer number = 1;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(roleid);
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

    @Override
    public List<Permission> queryAll() {
        String sql = "select * from t_permission";
        DaoMap dm = new DaoMap();
        Integer number = 5;
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);
        return this.makePermission(results);

    }


    @Override
    public List<Permission> queryPermissionsByUser(User user) {
        Permission permission = null;
        List<Permission> list = new ArrayList<>();
        String sql = "select * from t_permission where id in(select pid from t_role_permission where roleid in(select roleid from t_user_role where userid=?))";

        DaoMap dm = new DaoMap();
        List<Object> args = new ArrayList<>();
        args.add(user.getId());
        Integer number = 5;
        dm.setSql(sql);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);
        return this.makePermission(results);

    }

    @Override
    public void insertPermission(Permission permission) {

        if(null==permission){
            return ;
        }
        String sql = "insert into t_permission (name,url,pid) values(?,?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(permission.getName());
        args.add(permission.getUrl());
        args.add(permission.getPid());
        dm.setArgs(args);
        this.insert(dm);
    }

    @Override
    public Permission queryById(Integer permissionid) {

        String sql = "select * from t_permission where id=?";
        DaoMap dm = new DaoMap();
        Integer number = 5;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(permissionid);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);
        List<Permission> list = this.makePermission(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }

    @Override
    public void deletePermission(Integer id) {
        if(null==id){
            return ;
        }
        String sql = "delete from t_permission where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        this.delete(dm);

    }

    @Override
    public void updatePermission(Permission permission) {
        if(null==permission){
            return ;
        }
        String sql = "update t_permission set name=?,url=? where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(permission.getName());
        args.add(permission.getUrl());
        args.add(permission.getId());
        dm.setArgs(args);
        this.update(dm);
    }

    //封装权限对象
    private List<Permission> makePermission(List<Object> results) {
        List<Permission> list = new ArrayList<>();

        Permission permission = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String dbname = (String) values.get(1);
            String url = (String) values.get(2);
            Integer pid = (Integer) values.get(3);
            String icon = (String) values.get(4);
            permission = new Permission(id,dbname,url,pid,icon);

            list.add(permission);

        }

        return list;

    }



}
