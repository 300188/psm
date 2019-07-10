package cn.edu.mju.enums;

public enum DictionaryEnum {
    SUCCESS("验证通过",1),
    USERNAME_ERROR("用户名错误",-1),
    PASSWORD_ERROR("密码错误",-2),
    ERROR("验证失败",-3),
    NONE("账号或密码错误",-4),
    CODE_ERROR("验证码错误",0);
    private String msg;
    private int state;

    DictionaryEnum(String msg, int state) {
        this.msg = msg;
        this.state = state;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String ofState(int state){
        for (DictionaryEnum dictionary: DictionaryEnum.values()) {
            if (state == dictionary.getState()){
                return dictionary.getMsg();
            }
        }
        return null;
    }
}
