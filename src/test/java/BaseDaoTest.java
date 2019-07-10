import cn.edu.mju.dao.BaseDao;
import cn.edu.mju.dao.daoImpl.BaseDaoImpl;
import cn.edu.mju.dto.DaoMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BaseDaoTest {

    private BaseDao<Object> baseDao = new BaseDaoImpl<>();


    @Test
    public void query(){

        DaoMap dm = new DaoMap();
        String sql = "select * from t_user where id=?";
        List<Object> args  = new ArrayList<>();
        args.add(19);
        Integer number = 7;

        dm.setSql(sql);
        dm.setArgs(args);
        dm.setNumber(number);

        List<Object> results = baseDao.query(dm);


        for (Object result : results) {
            List<Object> r = (List<Object>) result;
            for (Object o : r) {

            System.out.print(o+"-->");
            }
        }


    }


    @Test
    public void insert(){

        DaoMap dm = new DaoMap();

        dm.setSql("insert into t_user(username,loginAccount,password) values(?,?,?)");

        List<Object> args = new ArrayList<>();
        args.add("测试用户");
        args.add("template");
        args.add("template");
        dm.setArgs(args);
        boolean result = baseDao.insert(dm);
        System.out.println(result);

    }


    @Test
    public void delete(){

        DaoMap dm = new DaoMap();

        dm.setSql("delete from t_user where username=?");

        List<Object> args = new ArrayList<>();
        args.add("测试用户");
        dm.setArgs(args);
        boolean result = baseDao.delete(dm);
        System.out.println(result);

    }



    @Test
    public void update(){


        DaoMap dm = new DaoMap();

        dm.setSql("update t_employee set name=? where id=?");

        List<Object> args = new ArrayList<>();
        args.add("小枫");
        args.add(63);
        dm.setArgs(args);
        boolean result = baseDao.update(dm);
        System.out.println(result);



    }



}
