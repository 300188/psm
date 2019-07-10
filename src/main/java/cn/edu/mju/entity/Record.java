package cn.edu.mju.entity;
//记录
public class Record  {


    private Integer id;

    private String text;

    private String createTime;

    private Employee employee;


    public Record(Integer id, String text, String createTime, Employee employee) {
        this.id = id;
        this.text = text;
        this.createTime = createTime;
        this.employee = employee;
    }


    public Record() {
    }

    public Record(String text, String createTime, Employee employee) {
        this.text = text;
        this.createTime = createTime;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
