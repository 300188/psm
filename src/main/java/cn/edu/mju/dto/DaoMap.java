package cn.edu.mju.dto;


import java.util.ArrayList;
import java.util.List;

//封装数据库参数
public class DaoMap {


    private String sql = "";
    //?的值
    private List<Object> args = new ArrayList<>();
    //sql中希望得到参数的个数
    private Integer number = 0;


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
