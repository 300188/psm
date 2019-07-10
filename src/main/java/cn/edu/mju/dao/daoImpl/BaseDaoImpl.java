package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.BaseDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDaoImpl<T> implements BaseDao<T> {


    private Connection connection = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;


    //查询
    @Override
    public List<Object> query(DaoMap dm) {

        if(dm!=null){
            String sql = dm.getSql();

            if(sql!=null&&!"".equals(sql)){
                try {

                    //获取链接
                    connection = DBUtils.getConnection();

                    preparedStatement = connection.prepareStatement(sql);

                    //获取需要预编译的参数
                    List<Object> args = dm.getArgs();

                    if(args!=null&&args.size()>0){

                        for (int i=0;i<args.size();i++){

                            preparedStatement.setObject(i+1,args.get(i));

                        }

                    }

                    resultSet = preparedStatement.executeQuery();

                    //存放需要返回的结果
                    List<Object> results = new ArrayList<>();

                    //获取需要返回的结果个数 也就是 sql语句中查询的内容个数
                    Integer number  = dm.getNumber();

                    while(resultSet.next()){

                        List<Object> result = new ArrayList<>();

                        for (int i = 1;i<=number;i++){
                            result.add(resultSet.getObject(i));
                        }
                        results.add(result);
                    }
                    DBUtils.close(connection,preparedStatement,resultSet);
                    return results;
                } catch (SQLException e) {
                    e.printStackTrace();
                    DBUtils.close(connection,preparedStatement,resultSet);
                }

            }

        }

        return null;
    }

    @Override
    public boolean update(DaoMap dm) {

        return this.template(dm);

    }

    @Override
    public boolean delete(DaoMap dm) {

        return this.template(dm);

    }

    @Override
    public boolean insert(DaoMap dm) {

        return this.template(dm);
    }


    private boolean template(DaoMap dm){

        if(dm!=null){
            String sql = dm.getSql();

            if(sql!=null&&!"".equals(sql)){
                try {

                    //获取链接
                    connection = DBUtils.getConnection();

                    preparedStatement = connection.prepareStatement(sql);

                    //获取需要预编译的参数
                    List<Object> args = dm.getArgs();

                    if(args!=null&&args.size()>0){

                        for (int i=0;i<args.size();i++){

                            preparedStatement.setObject(i+1,args.get(i));

                        }

                    }

                    preparedStatement.executeUpdate();

                    DBUtils.close(connection,preparedStatement,resultSet);
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    DBUtils.close(connection,preparedStatement,resultSet);
                }

            }

        }

        return false;

    }


}
