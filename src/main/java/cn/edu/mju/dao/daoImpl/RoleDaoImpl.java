package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.RoleDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Department;
import cn.edu.mju.entity.Role;
import cn.edu.mju.entity.User;
import cn.edu.mju.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {



    //查找所有的角色列表
    @Override
    public List<Role> queryAll() {

        String sql = "select * from t_role";
        DaoMap dm = new DaoMap();
        Integer number = 3;
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);

        //封装department 返回结果为List
        return this.makeRole(results);


    }

    private List<Role> makeRole(List<Object> results) {

        List<Role> list = new ArrayList<>();

        Role role = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String name = (String) values.get(1);
            String createTime = (String) values.get(2);

            role = new Role(id,name,createTime);
            list.add(role);
        }

        return list;
    }

    //分页查询角色数据
    @Override
    public List<Role> pageQueryData(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        Integer start = (Integer) map.get("start");
        Integer size = (Integer) map.get("size");
        String queryText = (String) map.get("queryText");
        String sql = null;
        if("".equals(queryText)||null==queryText){
            sql = "select * from t_role order by createTime desc limit ? , ?";
        }else{
            sql = "select * from t_role where name like concat('%',?,'%') order by createTime desc limit ?,?";
            args.add(queryText);
        }

        DaoMap dm = new DaoMap();
        Integer number = 3;
        args.add(start);
        args.add(size);
        dm.setArgs(args);
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);

        //封装user 返回结果为List
        return this.makeRole(results);



    }


    //查询角色总数
    @Override
    public int pageQueryCount(Map<String, Object> map) {

        List<Object> args = new ArrayList<>();

            String sql = "";
            if("".equals(map.get("queryText"))||map.get("queryText")==null){
                sql  = "select count(*) from t_role";
            }else {
                sql = "select count(*) from t_role where name like concat('%',?,'%')";
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


    //给角色分配权限
    @Override
    public void insertRolePermission(Map<String, Object> paramMap) {
        Integer roleid = (Integer) paramMap.get("roleid");
        String[] pids = (String[]) paramMap.get("permissionids");
        String sql = "insert into t_role_permission (roleid, pid) values(?,?)";


        DaoMap dm = new DaoMap();
        List<Object> args = new ArrayList<>();

        if (pids.length > 1) {
            for (int i = 1; i < pids.length; i++) {
                sql = sql + ",(?,?)";
            }
            for(int i = 0;i<pids.length;i++){
                args.add(roleid);
                args.add(Integer.parseInt(pids[i]));
            }
        } else{
            args.add(roleid);
            args.add(Integer.parseInt(pids[0]));
        }

        dm.setSql(sql);


        dm.setArgs(args);

        boolean tag = this.insert(dm);



    }


    @Override
    public int deleteRolePermissions(Map<String, Object> paramMap) {
        Integer id= (Integer) paramMap.get("roleid");
        int i=0;
        if(null==id){
            return i;
        }
        String sql = "delete from t_role_permission where roleid=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;

    }

    //根据id删除角色
    @Override
    public int deleteRoleById(Integer id) {
        int i = 0;
        if(null==id){
            return i;
        }
        String sql = "delete from t_role where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }


    //插入角色
    @Override
    public int insertRole(Role role) {
        int i = 0;
        if(null==role){
            return i;
        }
        String sql = "insert into t_role(name,createTime) values(?,?)";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(role.getName());
        args.add(role.getCreateTime());
        dm.setArgs(args);
        boolean tag = this.insert(dm);
        return tag?1:0;
    }

    //修改更新角色信息
    @Override
    public int update(Role role) {
        int i = 0;
        if(null==role){
            return i;
        }
        String sql = "update t_role set name=?,createTime=? where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(role.getName());
        args.add(role.getCreateTime());
        args.add(role.getId());
        dm.setArgs(args);
        boolean tag = this.update(dm);
        return tag?1:0;
    }

    //删除多个角色
    @Override
    public int deleteRoles(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();


        int i = 0;
        String[] ids = (String[]) map.get("roleids");
        if(null==ids){
            return i;
        }
        String sql = sql = "delete from t_role where id in(?";

        for (int j = 1;j<ids.length;j++) {
            sql = sql+",?";
        }
        sql = sql+")";

        for (int t = 0;t<ids.length;t++) {
            args.add(Integer.parseInt(ids[t]));
        }
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;


    }


    //根据id查找角色
    @Override
    public Role queryById(Integer id) {
        if(id==null){
            return null;
        }
        Role role = null;
        String sql = "select * from t_role where id=?";
        DaoMap dm = new DaoMap();
        Integer number = 3;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Role> list = this.makeRole(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    //检查角色
    @Override
    public List<Role> checkRole(Role role) {
        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = null;
        String sql = "select * from t_role where name=?";
        DaoMap dm = new DaoMap();
        Integer number = 3;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(name);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Role> list = this.makeRole(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
