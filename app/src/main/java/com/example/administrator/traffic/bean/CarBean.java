package com.example.administrator.traffic.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class CarBean {
    private List<BatchBean> batch;

    public List<BatchBean> getBatch() {
        return batch;
    }

    public void setBatch(List<BatchBean> batch) {
        this.batch = batch;
    }

    public static class BatchBean {
        /**
         * hphm : 湘AG1111
         * name : 张三
         * carlogo : http://192.168.1.103: 8080/images/baoma.jpg
         * Banlance : 100
         * CarId : 1
         */

        private String hphm;
        private String name;
        private String carlogo;
        private int Banlance;
        private int CarId;

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
}
