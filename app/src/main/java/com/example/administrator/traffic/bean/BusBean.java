package com.example.administrator.traffic.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class BusBean {
    public String Stance_name;
    public List<BusStance> busStanceList;
    private class BusStance {
        public String Bus_id;
        public String Bus_Stance;
    }
}
