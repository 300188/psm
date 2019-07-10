package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.PositionDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Position;
import cn.edu.mju.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PositionDaoImpl extends BaseDaoImpl<Position> implements PositionDao {


    @Override
    public List<Position> pageQueryData(Map<String, Object> map) {

            List<Object> args = new ArrayList<>();
            Integer start = (Integer) map.get("start");
            Integer size = (Integer) map.get("size");
            String queryText = (String) map.get("queryText");
            String sql = null;
            if("".equals(queryText)||null==queryText){
                sql = "select * from t_position order by createTime desc limit ? , ?";
            }else{
                sql = "select * from t_position where name like concat('%',?,'%') order by createTime desc limit ?,?";
                args.add(queryText);
            }
            args.add(start);
            args.add(size);
            DaoMap dm = new DaoMap();
            dm.setSql(sql);
            dm.setArgs(args);
            Integer number = 4;
            dm.setNumber(number);
            List<Object> results = this.query(dm);

            return this.makePosition(results);

    }


    //查询角色总数
    @Override
    public int pageQueryCount(Map<String, Object> map) {

        List<Object> args = new ArrayList<>();

        String sql = "";
        if("".equals(map.get("queryText"))||map.get("queryText")==null){
            sql  = "select count(*) from t_position";
        }else{
            sql = "select count(*) from t_position where name like concat('%',?,'%')";
            args.add(map.get("queryText"));
        }

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        dm.setArgs(args);
        Integer number = 1;
        dm.setNumber(number);
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

    //查询所有


    @Override
    public List<Position> queryAll() {
        Position position = null;
        List<Position> list = new ArrayList<>();
        String sql = "select * from t_position";
        DaoMap dm = new DaoMap();
        Integer number = 4;
        dm.setSql(sql);
        dm.setNumber(number);

        //查询
        List<Object> results = this.query(dm);

        //封装position 返回结果为List
        return this.makePosition(results);
    }

    //根据部门id查找对应的职位
    @Override
    public List<Position> queryByDepartmentid(int departmentid) {

        String sql = "select * from t_position where id in( select positionid from t_department_position where departmentid = ? )";
        DaoMap dm = new DaoMap();
        List<Object> args = new ArrayList<>();
        Integer number = 4;
        dm.setSql(sql);
        args.add(departmentid);
        dm.setArgs(args);
        dm.setNumber(number);

        //查询
        List<Object> results = this.query(dm);

        //封装position 返回结果为List
        return this.makePosition(results);
    }

    //根据部门id查找对应的职位
    @Override
    public List<Integer> queryPositionidByDepartmentid(Integer departmentid) {
        if(departmentid==null){
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        String sql = "select positionid from t_department_position where departmentid=?";
        DaoMap dm = new DaoMap();
        List<Object> args = new ArrayList<>();
        Integer number = 1;
        dm.setSql(sql);
        args.add(departmentid);
        dm.setArgs(args);
        dm.setNumber(number);

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

    //根据职位id查找职位信息
    @Override
    public Position queryById(Integer positionid) {
        if(positionid==null){
            return null;
        }
        Position position = null;
        String sql = "select * from t_position where id=?";
        DaoMap dm = new DaoMap();
        List<Object> args = new ArrayList<>();
        Integer number = 4;
        dm.setSql(sql);
        args.add(positionid);
        dm.setArgs(args);
        dm.setNumber(number);

        //查询
        List<Object> results = this.query(dm);

        //封装position 返回结果为List

        List<Position> list = this.makePosition(results);

        if(list.size()>0){
            return list.get(0);
        }

        return null;

    }


    //更新职位信息
    @Override
    public int update(Position position) {
        if(null==position){
            return 0;
        }
        String sql = "update t_position set name=?,createTime=? where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(position.getName());
        args.add(position.getCreateTime());
        args.add(position.getId());
        dm.setArgs(args);
        boolean tag = this.update(dm);
        return tag?1:0;
    }

    //更加职位名字查找职位信息
    @Override
    public Position queryByName(String name) {
        Position position = null;
        String sql = "select * from t_position where name=?";
        DaoMap dm = new DaoMap();
        List<Object> args = new ArrayList<>();
        Integer number = 4;
        dm.setSql(sql);
        args.add(name);
        dm.setArgs(args);
        dm.setNumber(number);

        //查询
        List<Object> results = this.query(dm);

        //封装position 返回结果为List

        List<Position> list = this.makePosition(results);

        if(list.size()>0){
            return list.get(0);
        }

        return null;
    }

    //增加职位信息
    @Override
    public int insert(Position position) {
        if(null==position){
            return 0;
        }
        String sql = "insert into t_position(name,createTime) values(?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(position.getName());
        args.add(position.getCreateTime());
        dm.setArgs(args);

        boolean tag = this.insert(dm);

        return tag?1:0;
    }


    //根据id删除职位

    @Override
    public int deletePositions(Map<String, Object> map) {
        String[] ids = (String[]) map.get("positions");
        if(null==ids){
            return 0;
        }
        String sql = sql = "delete from t_position where id in(?";

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


    //批量删除职位


    @Override
    public int deleteById(Integer positionid) {
        if(null==positionid){
            return 0;
        }
        String sql = "delete from t_position where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(positionid);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }


    //封装职位信息
    private List<Position> makePosition(List<Object> results){

        List<Position> list = new ArrayList<>();

        Position position = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String name = (String) values.get(1);
            String createTime = (String) values.get(2);
            Integer state = (Integer) values.get(3);

            position = new Position(id,name,createTime,state);
            list.add(position);
        }

        return list;


    }


}
