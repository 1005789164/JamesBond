package com.example.administrator.traffic.bean;

/**
 * Created by Administrator on 2018/1/13.
 */

public class RechargeBean {

    /**
     * date : 2018-01-12
     * UserName : user1
     * hphm : 湘AG4444
     * gold : 20
     * money : 21
     * time : 2018-01-12 08:32:28
     */

    private String date;
    private String UserName;
    private String hphm;
    private String gold;
    private String money;
    private String time;

    @Override
    public String toString() {
        return "{\"date\":"+"\""+date+"\""+",\"UserName\":"+"\""+UserName+"\""+",\"hphm\":"+"\""+hphm+"\""
                +",\"gold\":"+"\""+gold+"\""+",\"money\":"
                +"\""+money+"\""+",\"time\":"+"\""+time+"\""+"}";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
