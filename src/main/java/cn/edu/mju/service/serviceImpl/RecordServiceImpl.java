package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.RecordDao;
import cn.edu.mju.dao.daoImpl.RecordDaoImpl;
import cn.edu.mju.entity.Record;
import cn.edu.mju.service.RecordService;

import java.util.List;
import java.util.Map;

public class RecordServiceImpl extends BaseServiceImpl<Record> implements RecordService {

    private RecordDao recordDao = new RecordDaoImpl();

    @Override
    public List<Record> pageQueryData(Map<String, Object> map) {
        return recordDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return recordDao.pageQueryCount(map);
    }

    @Override
    public int update(Record record) {
        return recordDao.update(record);
    }

    @Override
    public int insert(Record record) {
        return recordDao.insert(record);
    }

    @Override
    public Record queryById(Integer recordid) {
        return recordDao.queryById(recordid);
    }

    @Override
    public int deleteById(Integer id) {
        return recordDao.deleteById(id);
    }

    @Override
    public int deletes(Map<String, Object> map) {
        return recordDao.deletes(map);
    }
}
