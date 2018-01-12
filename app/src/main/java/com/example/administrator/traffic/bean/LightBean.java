package com.example.administrator.traffic.bean;

/**
 * Created by 菠萝 on 2018/1/2.
 */

public class LightBean {
    public int crossing ;
    public int RedTime;
    public int YellowTime;
    public int GreenTime;

    @Override
    public String toString() {
        return "LightBean{" +
                "crossing=" + crossing +
                ", RedTime=" + RedTime +
                ", YellowTime=" + YellowTime +
                ", GreenTime=" + GreenTime +
                '}';
    }

    public int getCrossing() {
        return crossing;
    }

    public void setCrossing(int crossing) {
        this.crossing = crossing;
    }

    public int getRedTime() {
        return RedTime;
    }

    public void setRedTime(int redTime) {
        RedTime = redTime;
    }

    public int getYellowTime() {
        return YellowTime;
    }

    public void setYellowTime(int yellowTime) {
        YellowTime = yellowTime;
    }

    public int getGreenTime() {
        return GreenTime;
    }

    public void setGreenTime(int greenTime) {
        GreenTime = greenTime;
    }
}
