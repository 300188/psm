package cn.edu.mju.entity;

//日志
public class Log {

    private Integer id;

    private String log;

    private String createTime;


    public Log(Integer id, String log, String createTime) {
        this.id = id;
        this.log = log;
        this.createTime = createTime;
    }


    public Log() {
    }

    public Integer getId() {
        return id;
    }

    public Log(String log, String createTime) {
        this.log = log;
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
