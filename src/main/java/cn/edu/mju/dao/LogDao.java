package cn.edu.mju.dao;

import cn.edu.mju.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogDao extends BaseDao<Log> {
    int insert(Log log);

    int deleteById(Integer id);

    int deletes(Map<String, Object> map);

    List<Log> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    void deleteByLog(Log log);
}
