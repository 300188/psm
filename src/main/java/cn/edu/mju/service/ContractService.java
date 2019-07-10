package cn.edu.mju.service;

import cn.edu.mju.entity.Contract;

import java.util.List;
import java.util.Map;

public interface ContractService extends BaseService<Contract> {
    List<Contract> pageQueryData(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    int deleteById(Integer id);

    Contract queryById(Integer contractid);

    int deleteContracts(Map<String, Object> map);

    int insert(Contract contract);

    int update(Contract contract);
}
