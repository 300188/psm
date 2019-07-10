package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.LogDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Log;
import cn.edu.mju.entity.Record;
import cn.edu.mju.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogDaoImpl extends BaseDaoImpl<Log> implements LogDao {

    @Override
    public int insert(Log log) {
        if(null==log){
            return 0;
        }
        String sql = "insert into t_log(log,createTime) values (?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(log.getLog());
        args.add(log.getCreateTime());
        dm.setArgs(args);
        return this.insert(dm)?1:0;
    }

    @Override
    public int deleteById(Integer id) {
        int i = 0;
        if(null==id){
            return i;
        }
        String sql = "delete from t_log where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }

    @Override
    public int deletes(Map<String, Object> map) {
        int i = 0;
        String[] ids = (String[]) map.get("logids");
        if(null==ids){
            return i;
        }
        String sql = sql = "delete from t_log where id in(?";

        for (int j = 1;j<ids.length;j++) {
            sql = sql+",?";
        }
        sql = sql+")";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        for (int t = 0;t<ids.length;t++) {
            args.add(Integer.parseInt(ids[t]));
        }
        dm.setArgs(args);

        boolean tag = this.delete(dm);
        return tag?1:0;
    }

    @Override
    public List<Log> pageQueryData(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        List<User> list = new ArrayList<>();

        Integer start = (Integer) map.get("start");
        Integer size = (Integer) map.get("size");
        String queryText = (String) map.get("queryText");
        String sql = null;
        if("".equals(queryText)||null==queryText){
            sql = "select * from t_log order by createTime desc limit ? , ?";
        }else{
            sql = "select * from t_log where log like concat('%',?,'%') order by createTime desc limit ?,?";
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
        return this.makeLog(results);
    }


    @Override
    public void deleteByLog(Log log) {
        if(null==log){
            return ;
        }
        String sql = "delete from t_log where log=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(log.getLog());
        dm.setArgs(args);
        this.delete(dm);
    }

    private List<Log> makeLog(List<Object> results) {
        List<Log> list = new ArrayList<>();

        Log log = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String text = (String) values.get(1);
            String createTime = (String) values.get(2);

            log = new Log(id,text,createTime);
            list.add(log);
        }

        return list;

    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        String sql = "";
        if("".equals(map.get("queryText"))||map.get("queryText")==null){
            sql  = "select count(*) from t_log";
        }else {
            sql = "select count(*) from t_log where log like concat('%',?,'%')";
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
}
