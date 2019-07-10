package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.LogDao;
import cn.edu.mju.dao.daoImpl.LogDaoImpl;
import cn.edu.mju.entity.Log;
import cn.edu.mju.service.LogService;

import java.util.List;
import java.util.Map;

public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {

    private LogDao logDao = new LogDaoImpl();

    @Override
    public int insert(Log log) {
        return logDao.insert(log);
    }

    @Override
    public int deleteById(Integer id) {
        return logDao.deleteById(id);
    }

    @Override
    public int deletes(Map<String, Object> map) {
        return logDao.deletes(map);
    }

    @Override
    public List<Log> pageQueryData(Map<String, Object> map) {
        return logDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return logDao.pageQueryCount(map);
    }

    @Override
    public void deleteByLog(Log log) {
        logDao.deleteByLog(log);
    }
}
