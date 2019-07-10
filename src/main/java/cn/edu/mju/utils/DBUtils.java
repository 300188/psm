package cn.edu.mju.utils;

import cn.edu.mju.service.RoleService;
import cn.edu.mju.service.UserService;
import cn.edu.mju.service.serviceImpl.RoleServiceImpl;
import cn.edu.mju.service.serviceImpl.UserServiceImpl;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DBUtils {

    private static DBPool pool = new DBPool();

    //从连接池中获取一个连接
    public static Connection getConnection() {
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            System.out.println("fail to getConnection");
            return null;
        }
    }

    //关闭连接
    public static boolean close(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(null!=resultSet){
                resultSet.close();
            }
            if(statement !=null){

                statement.close();
            }
            if(connection!=null){

                connection.close();
            }
        } catch (SQLException e) {
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e1) {
                    resultSet = null;
                    try {
                        statement.close();
                    } catch (SQLException e2) {
                        if(statement!=null){
                            try {
                                statement.close();
                            } catch (SQLException e3) {
                                statement = null;
                                try {
                                    connection.close();
                                } catch (SQLException e4) {
                                    e4.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }



}
