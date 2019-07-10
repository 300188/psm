package cn.edu.mju.entity;

//职位
public class Position {

    private Integer id;
    private String name = "";
    private String createTime;
    private Integer state;


    public Position(Integer id, String name, String createTime, Integer state) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.state = state;
    }

    public Position() {
    }



    public Position(String name, String createTime) {
        this.name = name;
        this.createTime = createTime;
    }

    public Position(Integer id, String name, String createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
