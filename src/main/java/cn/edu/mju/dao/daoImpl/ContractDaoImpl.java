package cn.edu.mju.dao.daoImpl;

import cn.edu.mju.dao.BaseDao;
import cn.edu.mju.dao.ContractDao;
import cn.edu.mju.dao.EmployeeDao;
import cn.edu.mju.dto.DaoMap;
import cn.edu.mju.entity.Contract;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.User;
import cn.edu.mju.service.ContractService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao {

    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public List<Contract> pageQueryData(Map<String, Object> map) {
        List<Object> args = new ArrayList<>();

        List<User> list = new ArrayList<>();

        Integer start = (Integer) map.get("start");
        Integer size = (Integer) map.get("size");
        String queryText = (String) map.get("queryText");
        String sql = null;
        if("".equals(queryText)||null==queryText){
            sql = "select * from t_contract order by createTime desc limit ? , ?";
        }else{
            sql = "select * from t_contract where name like concat('%',?,'%') order by createTime desc limit ?,?";
            args.add(queryText);
        }
        DaoMap dm = new DaoMap();
        Integer number = 6;
        args.add(start);
        args.add(size);
        dm.setArgs(args);
        dm.setSql(sql);
        dm.setNumber(number);
        //查询
        List<Object> results = this.query(dm);

        //封装user 返回结果为List
        return this.makeContract(results);
    }

    private List<Contract> makeContract(List<Object> results) {

        List<Contract> list = new ArrayList<>();

        Contract contract = null;

        for (Object result : results) {

            List<Object> values = (List<Object>) result;

            Integer id = (Integer) values.get(0);
            String name = (String) values.get(1);
            String createTime = (String) values.get(2);
            Integer limit = (Integer) values.get(3);
            Integer employeeid = (Integer) values.get(4);
            Employee employee = null;
            if(employeeid!=null){
                employee = employeeDao.queryById(employeeid);
            }

            if(employee==null){
                employee = new Employee();
            }

            Integer state = (Integer) values.get(5);

            contract = new Contract(id,name,createTime,employee,limit,state);
            list.add(contract);
        }

        return list;
    }

    @Override
    public int pageQueryCount(Map<String, Object> map) {

        List<Object> args = new ArrayList<>();

        String sql = "";
        if("".equals(map.get("queryText"))||map.get("queryText")==null){
            sql  = "select count(*) from t_contract";
        }else {
            sql = "select count(*) from t_contract where name like concat('%',?,'%')";
            args.add(map.get("queryText"));
        }

        DaoMap dm = new DaoMap();
        Integer number = 1;
        dm.setSql(sql);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);
        if (results != null && results.size() > 0) {
            List<Object> values = (List<Object>) results.get(0);
            if (values != null && values.size() > 0) {
                return ((Number)values.get(0)).intValue();
            } else {
                return 0;
            }

        }
        return 0;
    }


    @Override
    public int deleteById(Integer id) {
        int i = 0;
        if(null==id){
            return i;
        }
        String sql = "delete from t_contract where id=?";
        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(id);
        dm.setArgs(args);
        boolean tag = this.delete(dm);
        return tag?1:0;
    }

    @Override
    public Contract queryById(Integer contractid) {
        if(contractid==null){
            return null;
        }
        Contract contract = null;
        String sql = "select * from t_contract where id=?";

        DaoMap dm = new DaoMap();
        Integer number = 6;
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(contractid);
        dm.setNumber(number);
        dm.setArgs(args);
        //查询
        List<Object> results = this.query(dm);

        List<Contract> list = this.makeContract(results);

        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }

    @Override
    public int deleteContracts(Map<String, Object> map) {
        int i = 0;
        String[] ids = (String[]) map.get("contractids");
        if(null==ids){
            return i;
        }
        String sql = sql = "delete from t_contract where id in(?";

        for (int j = 1;j<ids.length;j++) {
            sql = sql+",?";
        }
        sql = sql+")";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        for (int t = 0;t<ids.length;t++) {
            args.add(Integer.parseInt(ids[t]));
        }
        dm.setArgs(args);

        boolean tag = this.delete(dm);
        return tag?1:0;
    }


    @Override
    public int insert(Contract contract) {
        if(null==contract){
            return 0;
        }
        String sql = "insert into t_contract(name,createTime,lim,employeeid) values (?,?,?,?)";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(contract.getName());
        args.add(contract.getCreateTime());
        args.add(contract.getLimit());
        args.add(contract.getEmployee().getId());
        dm.setArgs(args);
        return this.insert(dm)?1:0;
    }

    @Override
    public int update(Contract contract) {
        if(null==contract){
            return 0;
        }
        String sql = "update t_contract set name=?,createTime=?,lim=? where id=?";

        DaoMap dm = new DaoMap();
        dm.setSql(sql);
        List<Object> args = new ArrayList<>();
        args.add(contract.getName());
        args.add(contract.getCreateTime());
        args.add(contract.getLimit());
        args.add(contract.getId());
        dm.setArgs(args);
        boolean tag = this.update(dm);
        return tag?1:0;
    }
}
