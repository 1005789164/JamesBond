package com.example.administrator.traffic.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ViolationBean {

    /**
     * province : GD
     * city : GD_JM
     * hphm : 湘AG1111
     * hpzl : 02
     * lists : [{"date":"2017-07-11 11:45:00","area":"港湾大道银坑村路口","act":"驾驶机动车在划有导向车道的路口，不按所需行进方向驶入导向车道，不按规定方向行驶的","code":"4404067900172059","fen":2,"wzcity":"湖南长沙","money":300,"handled":0,"archiveno":"4404067900172059","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"},{"date":" 2017-10-04 20:22:33","area":"番禺大道富华东路路口","act":"机动车通过有灯控路口时，不按所需行进方向驶入导向车道的","code":"4401267902030070","fen":2,"wzcity":"广东广州","money":100,"handled":0,"archiveno":"4401267902030070","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"},{"date":"2017-10-21 11:20:00","area":"广州绕城高速145公里200米","act":"驾驶中型以上载客载货汽车、危险物品运输车辆以外的其他机动车行驶超过规定时速10%未达20%的","code":"4406997901477224","fen":3,"wzcity":"广东佛山","money":150,"handled":0,"archiveno":"4406997901477224","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"},{"date":"2017-11-21 17:52:06","area":"广州市番禺区广场东路路段","act":"机动车违反禁令标志指示的","code":"4401267902177105","fen":3,"wzcity":"广东广州","money":200,"handled":0,"archiveno":"4401267902177105","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"},{"date":"2017-09-15 11:40:00","area":"广州市番禺区嘉胜路","act":"机动车违反规定停放、临时停车，妨碍其它车辆、行人通行的","code":"4401267901951920","fen":0,"wzcity":"广东广州","money":200,"handled":0,"archiveno":"4401267901951920","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"},{"date":"2017-10-03 22:03:00","area":"螺山路环村路路口","act":"通过路口遇停止信号时，停在停止线以内或路口内的","code":"4401267902027680","fen":2,"wzcity":"广东广州","money":100,"handled":0,"archiveno":"4401267902027680","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"},{"date":"2017-10-29 20:31:00","area":"桥南街南堤东路","act":"机动车违反规定停放、临时停车，妨碍其它车辆、行人通行的","code":"4401267902105566","fen":0,"wzcity":"广东广州","money":200,"handled":0,"archiveno":"4401267902105566","photo":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg"}]
     * error_code : 0
     */

    private String province;
    private String city;
    private String hphm;
    private String hpzl;
    private int error_code;
    private List<ListsBean> lists;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * date : 2017-07-11 11:45:00
         * area : 港湾大道银坑村路口
         * act : 驾驶机动车在划有导向车道的路口，不按所需行进方向驶入导向车道，不按规定方向行驶的
         * code : 4404067900172059
         * fen : 2
         * wzcity : 湖南长沙
         * money : 300
         * handled : 0
         * archiveno : 4404067900172059
         * photo : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514542087863&di=034f4a3fd4cf15193e2d8ee4bb410b9a&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D2643142925%2C388674453%26fm%3D214%26gp%3D0.jpg
         */

        private String date;
        private String area;
        private String act;
        private String code;
        private int fen;
        private String wzcity;
        private int money;
        private int handled;
        private String archiveno;
        private String photo;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getFen() {
            return fen;
        }

        public void setFen(int fen) {
            this.fen = fen;
        }

        public String getWzcity() {
            return wzcity;
        }

        public void setWzcity(String wzcity) {
            this.wzcity = wzcity;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getHandled() {
            return handled;
        }

        public void setHandled(int handled) {
            this.handled = handled;
        }

        public String getArchiveno() {
            return archiveno;
        }

        public void setArchiveno(String archiveno) {
            this.archiveno = archiveno;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
