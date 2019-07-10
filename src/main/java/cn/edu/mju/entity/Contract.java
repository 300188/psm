package cn.edu.mju.entity;

//合同
public class Contract {

    private Integer id;

    private String name;

    private String createTime;

    private Employee employee;

    //期限
    private Integer limit;

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Contract() {
    }

    public Contract(String name, String createTime, Employee employee, Integer limit, Integer state) {
        this.name = name;
        this.createTime = createTime;
        this.employee = employee;
        this.limit = limit;
        this.state = state;
    }

    public Contract(Integer id, String name, String createTime, Employee employee, Integer limit, Integer state) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.employee = employee;
        this.limit = limit;
        this.state = state;
    }

    public Contract(Integer id, String name, String createTime, Employee employee, Integer limit) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.employee = employee;
        this.limit = limit;
    }

    public Contract(String name, String createTime, Employee employee, Integer limit) {
        this.name = name;
        this.createTime = createTime;
        this.employee = employee;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
