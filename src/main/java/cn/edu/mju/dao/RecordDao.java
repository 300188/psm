package cn.edu.mju.dao;

import cn.edu.mju.entity.Record;

import java.util.List;
import java.util.Map;

public interface RecordDao extends BaseDao<Record> {


    List<Record> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);


    int deleteById(Integer id);

    Record queryById(Integer recordid);

    int deletes(Map<String, Object> map);

    int insert(Record record);

    int update(Record record);
}
