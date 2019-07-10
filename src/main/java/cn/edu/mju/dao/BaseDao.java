package cn.edu.mju.dao;

import cn.edu.mju.dto.DaoMap;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    List<Object> query(DaoMap dm);

    boolean update(DaoMap dm);

    boolean delete(DaoMap dm);

    boolean insert(DaoMap dm);

}
