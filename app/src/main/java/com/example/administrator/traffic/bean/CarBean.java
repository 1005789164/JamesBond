package com.example.administrator.traffic.bean;

/**
 * Created by Administrator on 2018/1/8.
 */

public class CarBean {


    /**
     * hphm : 湘AG1111
     * name : 张三
     * carlogo : http://192.168.1.103:8080/transportservice/imgs/car-bm.png
     * Banlance : 100
     * CarId : 1
     */

    private String hphm;
    private String name;
    private String carlogo;
    private int Banlance;
    private int CarId;

    @Override
    public String toString() {
        return "{" +
                "hphm:" + hphm + '\'' +
                ", name:" + name + '\'' +
                ", carlogo:" + carlogo + '\'' +
                ", Banlance=" + Banlance +
                ", CarId=" + CarId +
                '}';
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarlogo() {
        return carlogo;
    }

    public void setCarlogo(String carlogo) {
        this.carlogo = carlogo;
    }

    public int getBanlance() {
        return Banlance;
    }

    public void setBanlance(int Banlance) {
        this.Banlance = Banlance;
    }

    public int getCarId() {
        return CarId;
    }

    public void setCarId(int CarId) {
        this.CarId = CarId;
    }
}
