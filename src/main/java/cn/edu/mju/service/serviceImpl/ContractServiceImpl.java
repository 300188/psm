package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.ContractDao;
import cn.edu.mju.dao.daoImpl.ContractDaoImpl;
import cn.edu.mju.entity.Contract;
import cn.edu.mju.service.ContractService;

import java.util.List;
import java.util.Map;

public class ContractServiceImpl extends BaseServiceImpl<Contract> implements ContractService {

    private ContractDao contractDao = new ContractDaoImpl();

    @Override
    public List<Contract> pageQueryData(Map<String, Object> map) {
        return contractDao.pageQueryData(map);
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {
        return contractDao.pageQueryCount(map);
    }

    @Override
    public int deleteById(Integer id) {
        return contractDao.deleteById(id);
    }

    @Override
    public Contract queryById(Integer contractid) {
        return contractDao.queryById(contractid);
    }

    @Override
    public int deleteContracts(Map<String, Object> map) {
        return contractDao.deleteContracts(map);
    }

    @Override
    public int insert(Contract contract) {
        return contractDao.insert(contract);
    }

    @Override
    public int update(Contract contract) {
        return contractDao.update(contract);
    }
}
