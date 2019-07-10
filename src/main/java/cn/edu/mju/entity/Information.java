package cn.edu.mju.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

//员工详情信息实体类
public class Information {

    private int id;
    private String name;
    private int age;
    private String idCard;
    private String phone;
    private int sex;
    private String address;
    private int status;
    private int ismarried;
    private String image;
    private String email;
    private String edu;
    private String createTime;
    private Integer eid;

    private String date;

    public Information(int id, String name, int age, String idCard, String phone, int sex, String address, int status, int ismarried, String image, String email, String edu, String createTime, Integer eid, String date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.idCard = idCard;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.status = status;
        this.ismarried = ismarried;
        this.image = image;
        this.email = email;
        this.edu = edu;
        this.createTime = createTime;
        this.eid = eid;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Information() {
    }

    public Information(int id, String name, int age, String idCard, String phone, int sex, String address, int status, int ismarried, String image, String email, String edu, String createTime, Integer eid) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.idCard = idCard;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.status = status;
        this.ismarried = ismarried;
        this.image = image;
        this.email = email;
        this.createTime = createTime;
        this.eid = eid;
        this.edu = edu;
    }

    public Information(int id, String name, String idCard, String phone, int sex, String address, int status, int ismarried, String image, String email, String edu, String createTime, Integer eid, String date) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.status = status;
        this.ismarried = ismarried;
        this.image = image;
        this.email = email;
        this.edu = edu;
        this.createTime = createTime;
        this.eid = eid;
        this.date = date;
    }

    public Information(String name, String idCard, String phone, int sex, String address, int status, int ismarried, String image, String email, String edu, String createTime, Integer eid, String date) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.status = status;
        this.ismarried = ismarried;
        this.image = image;
        this.email = email;
        this.edu = edu;
        this.createTime = createTime;
        this.eid = eid;
        this.date = date;
    }

    public Information(String name, int age, String idCard, String phone, int sex, String address, int status, int ismarried, String image, String email, String edu, String createTime, Integer eid, String date) {
        this.name = name;
        this.age = age;
        this.idCard = idCard;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.status = status;
        this.ismarried = ismarried;
        this.image = image;
        this.email = email;
        this.edu = edu;
        this.createTime = createTime;
        this.eid = eid;
        this.date = date;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        int age = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date birthDay = dateFormat.parse(this.date);
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            cal.setTime(birthDay);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
        } catch (ParseException e) {

        }
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsmarried() {
        return ismarried;
    }

    public void setIsmarried(int ismarried) {
        this.ismarried = ismarried;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", ismarried=" + ismarried +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", edu='" + edu + '\'' +
                ", createTime='" + createTime + '\'' +
                ", eid=" + eid +
                ", date='" + date + '\'' +
                '}';
    }


    public static void main(String[] args) {

    }
}
