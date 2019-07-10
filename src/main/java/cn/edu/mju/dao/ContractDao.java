package cn.edu.mju.dao;

import cn.edu.mju.entity.Contract;
import cn.edu.mju.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ContractDao extends BaseDao<Contract> {
    List<Contract> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    int deleteById(Integer id);

    Contract queryById(Integer contractid);

    int deleteContracts(Map<String, Object> map);

    int insert(Contract contract);

    int update(Contract contract);
}
