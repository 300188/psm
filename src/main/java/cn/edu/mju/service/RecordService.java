package cn.edu.mju.service;

import cn.edu.mju.entity.Record;

import java.util.List;
import java.util.Map;

public interface RecordService extends BaseService<Record> {
    List<Record> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    int update(Record record);

    int insert(Record record);

    Record queryById(Integer recordid);

    int deleteById(Integer id);

    int deletes(Map<String, Object> map);
}
