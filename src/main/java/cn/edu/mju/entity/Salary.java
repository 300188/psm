package cn.edu.mju.entity;

//员工工资
public class Salary {

    private Integer id;

    private String name;

    private Integer salary;

    private Integer employeeid;

    private Employee employee;

    public Salary() {
    }

    public Salary(Integer id, String name, Integer salary, Integer employeeid, Employee employee) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.employeeid = employeeid;
        this.employee = employee;
    }


    public Salary(String name, Integer salary, Integer employeeid, Employee employee) {
        this.name = name;
        this.salary = salary;
        this.employeeid = employeeid;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
