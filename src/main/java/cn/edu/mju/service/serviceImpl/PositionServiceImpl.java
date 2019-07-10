package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.PositionDao;
import cn.edu.mju.dao.daoImpl.PositionDaoImpl;
import cn.edu.mju.entity.Position;
import cn.edu.mju.service.PositionService;

import java.util.List;
import java.util.Map;

public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService {

    private PositionDao positionDao = new PositionDaoImpl();

    @Override
    public List<Position> queryAll() {
        return positionDao.queryAll();
    }

    @Override
    public List<Position> queryByDepartmentid(int departmentid) {
        return positionDao.queryByDepartmentid(departmentid);
    }

    @Override
    public Position queryById(Integer positionid) {
        return positionDao.queryById(positionid);
    }

    @Override
    public int update(Position position) {
        return positionDao.update(position);
    }

    @Override
    public List<Position> pageQueryData(Map<String, Object> map) {
        return positionDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return positionDao.pageQueryCount(map);
    }


    @Override
    public Position queryByName(String name) {
        return positionDao.queryByName(name);
    }

    @Override
    public int insert(Position position) {
        return positionDao.insert(position);
    }

    @Override
    public int deleteById(Integer positionid) {
        return positionDao.deleteById(positionid);
    }

    @Override
    public int deletePositions(Map<String, Object> map) {
        return positionDao.deletePositions(map);
    }
}
