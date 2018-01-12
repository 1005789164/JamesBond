package com.example.administrator.traffic.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class BusBean {

        /**
         * BusId : 1
         * Busload : 60
         * Distance : 975
         */

        private int BusId;
        private int Busload;
        private int Distance;

    @Override
    public String toString() {
        return "BusBean{" +
                "BusId=" + BusId +
                ", Busload=" + Busload +
                ", Distance=" + Distance +
                '}';
    }

    public int getBusId() {
            return BusId;
        }

        public void setBusId(int BusId) {
            this.BusId = BusId;
        }

        public int getBusload() {
            return Busload;
        }

        public void setBusload(int Busload) {
            this.Busload = Busload;
        }

        public int getDistance() {
            return Distance;
        }

        public void setDistance(int Distance) {
            this.Distance = Distance;
        }

}
