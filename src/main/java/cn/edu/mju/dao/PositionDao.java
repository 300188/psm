package cn.edu.mju.dao;

import cn.edu.mju.entity.Position;

import java.util.List;
import java.util.Map;

public interface PositionDao extends BaseDao<Position> {
    List<Position> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    List<Position> queryAll();

    List<Position> queryByDepartmentid(int departmentid);

    List<Integer> queryPositionidByDepartmentid(Integer departmentid);

    Position queryById(Integer positionid);

    int update(Position position);

    Position queryByName(String name);

    int insert(Position position);

    int deleteById(Integer positionid);

    int deletePositions(Map<String, Object> map);
}
