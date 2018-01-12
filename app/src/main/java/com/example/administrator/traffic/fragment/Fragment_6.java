package com.example.administrator.traffic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.traffic.MainActivity;
import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyPagerAdpter;
import com.example.administrator.traffic.http.HttpThread;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_6 extends Fragment {
    public static final int FRAGMENT6_HANDLER1=601;
    private SlidingMenu menu;
    private LineChart lc;
    private ViewPager vp;
    private RelativeLayout relativeLayout;
    private ArrayList<View> list = new ArrayList<View>();
    private TextView[] textViews = new TextView[4];
    private TextView airWeather, temperWeather, humidityWeather;
    private BarChart airBarc;
    private PieChart temperPiec;
    private LineChart humidityLinc;
    private TextView ziwaixiang1;
    private TextView ziwaixiang2;
    private TextView ganmao1;
    private TextView ganmao2;
    private TextView chuanyi1;
    private TextView chuanyi2;
    private TextView yundong1;
    private TextView yundong2;
    private TextView kongqi1;
    private TextView kongqi2;
    private TextView tvair;
    private TextView tvtoday;
    private Timer timer;
    //private GraphicalView graphicalView;  Achart引擎图

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6, null);
        menu = ((MainActivity) getActivity()).slidingMenu;
        initView(view);
        return view;
    }

    private void Net() {
        new HttpThread("getWeather.do","",handler1,Fragment_6.FRAGMENT6_HANDLER1).start();
    }

    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==Fragment_6.FRAGMENT6_HANDLER1){
                try {
                    JSONObject json=new JSONObject(((String)msg.obj).substring(9).replace(")",""));
                    json=json.getJSONObject("life");
                    json=json.getJSONObject("info");
                    JSONArray jsonArray= new JSONObject(((String)msg.obj).substring(9).replace(")",""))
                            .getJSONArray("hourly_forecast");
                    String[] strs=new String[24];
                    for (int i=0;i<=23;i++){
                        strs[i]=jsonArray.getJSONObject(i).getString("temperature");
                    }
                    Arrays.sort(strs);
                    tvtoday.setText("今天："+strs[0]+"-"+strs[23]+"°C");
                    tvair.setText(new JSONObject(((String)msg.obj).substring(9).replace(")","")).getJSONObject("realtime").getJSONObject("weather").getString("temperature")+"°");
                    ziwaixiang1.setText(json.getJSONArray("ziwaixian").getString(0));
                    ziwaixiang2.setText(json.getJSONArray("ziwaixian").getString(1));
                    ganmao1.setText(json.getJSONArray("ganmao").getString(0));
                    ganmao2.setText(json.getJSONArray("ganmao").getString(1));
                    chuanyi1.setText(json.getJSONArray("chuanyi").getString(0));
                    chuanyi2.setText(json.getJSONArray("chuanyi").getString(1));
                    yundong1.setText(json.getJSONArray("yundong").getString(0));
                    yundong2.setText(json.getJSONArray("yundong").getString(1));
                    kongqi1.setText(json.getJSONArray("wuran").getString(0));
                    kongqi2.setText(json.getJSONArray("wuran").getString(1));
                    Log.e("xxx",json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("xxx",((String)msg.obj).substring(9).replace(")",""));
            }
        }
    };

    @Override
    public void onResume() {
        (timer=new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                Net();
            }
        },300,3000);
        super.onResume();
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lc = (LineChart) view.findViewById(R.id.lc);
        menu.addIgnoredView(lc);
        createPolyLine();
        vp = (ViewPager) view.findViewById(R.id.vp);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rlayout);
        menu.addIgnoredView(relativeLayout);
        (textViews[0] = (TextView) view.findViewById(R.id.tvweatherbtn)).setBackgroundColor(Color.rgb(182, 182, 182));
        textViews[1] = (TextView) view.findViewById(R.id.tvc);
        textViews[2] = (TextView) view.findViewById(R.id.tvd);
        textViews[3] = (TextView) view.findViewById(R.id.tvco2);
        point();
    }

    private void point() {
        LayoutInflater layoutInflater;
        View v;
        layoutInflater = LayoutInflater.from(this.getContext());
        v = layoutInflater.inflate(R.layout.air_item, null);
        airWeather = (TextView) v.findViewById(R.id.tvweather);
        airBarc = (BarChart) v.findViewById(R.id.barc);
        createColumnar();
        list.add(v);
        v = layoutInflater.inflate(R.layout.temper_item, null);
        temperWeather = (TextView) v.findViewById(R.id.tvweather);
        temperPiec = (PieChart) v.findViewById(R.id.piec);
        createPie();
        list.add(v);
        v = layoutInflater.inflate(R.layout.humidity_item, null);
        humidityWeather = (TextView) v.findViewById(R.id.tvweather);
        humidityLinc = (LineChart) v.findViewById(R.id.linc);
        createHumidity();
        list.add(v);
        v = layoutInflater.inflate(R.layout.co2_item, null);
        list.add(v);
        vp.setAdapter(new MyPagerAdpter(list));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < textViews.length; i++) {
                    if (i != position)
                        textViews[i].setBackgroundColor(Color.argb(0, 0, 0, 0));
                    if (i == position)
                        textViews[position].setBackgroundColor(Color.rgb(182, 182, 182));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createHumidity() {
        humidityLinc.setNoDataText("没有数据");
        humidityLinc.setDescription("");
        humidityLinc.setDrawGridBackground(false);
        humidityLinc.setDrawBorders(false);
        humidityLinc.setBackgroundColor(Color.rgb(253,253,254));
        XAxis xAxis = humidityLinc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        YAxis axisleft = humidityLinc.getAxisLeft();
        axisleft.setDrawGridLines(true);
        axisleft.setDrawAxisLine(false);
        axisleft.setLabelCount(6, true);
        axisleft.setAxisMaxValue(65f);
        axisleft.setAxisMinValue(30f);
        axisleft.setStartAtZero(false);
        YAxis axisRight = humidityLinc.getAxisRight();
        axisRight.setEnabled(false);
        ArrayList<Entry> list = new ArrayList<>();
        list.add(new Entry(35f, 0));
        list.add(new Entry(30f, 1));
        list.add(new Entry(35f, 2));
        list.add(new Entry(36f, 3));
        list.add(new Entry(59f, 4));
        list.add(new Entry(39f, 5));
        list.add(new Entry(45f, 6));
        ArrayList<String> x = new ArrayList<>();
        for (int i = 3; i <= list.size() * 3; i += 3) {
            x.add(i + "");
        }
        for (int i = 0; i < list.size() / 3; i++) {
            x.add("");
        }
        x.add("(S)");
        xAxis.setValueFormatter(new XAxisValueFormatter() {
            @Override
            public String getXValue(String s, int i, ViewPortHandler viewPortHandler) {
                if (i < 3)
                    s = "0" + s;
                return s;
            }
        });
        LineDataSet dataset = new LineDataSet(list, "one");
        dataset.setCircleColor(Color.BLACK);
        dataset.setDrawCircleHole(false);//实心
        dataset.setColor(Color.GRAY);//线条
        dataset.setDrawValues(false);
        LineData lineData = new LineData(x, dataset);
        humidityLinc.setData(lineData);
        humidityLinc.invalidate();
    }

    private void createPie() {
        temperPiec.setNoDataText("没有数据");
        temperPiec.setDescription("");//描述信息
        // 设置偏移量
        temperPiec.setExtraOffsets(5, 10, 5, 5);
        // 设置滑动减速摩擦系数
        temperPiec.setDragDecelerationFrictionCoef(0.95f);
        temperPiec.setDrawHoleEnabled(false);
        Legend l = temperPiec.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        ArrayList<String> xValue = new ArrayList<>();
        xValue.add("有违章");
        xValue.add("无违章");
        ArrayList<Entry> yValue = new ArrayList<>();
        yValue.add(new Entry(28.6f, 0));
        yValue.add(new Entry(71.3f, 1));
        PieDataSet pieDataSet = new PieDataSet(yValue, "");
        pieDataSet.setColors(new int[]{Color.BLUE, Color.RED});
        PieData pieData = new PieData(xValue, pieDataSet);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                String str = v + "%";
                return str;
            }
        });
        temperPiec.setData(pieData);
        temperPiec.invalidate();
    }

    /*
    private void createPie() {
        // 设置数据源
        double[] values={412.0,542.0,486.0,900.1};
        CategorySeries dataset=buildCategoryDataset("测试饼图", values);

        // 设置渲染参数
        int[] colors={Color.BLUE,Color.GREEN,Color.MAGENTA,Color.RED};
        DefaultRenderer renderer=buildCategoryRenderer(colors);

        // 生成图表
        // 其中的dataset表示数据源，renderer表示渲染参数，type表示类型
        graphicalView= ChartFactory.getPieChartView(getContext(), dataset, renderer);//饼状图
        // 指定 layout 来显示图表
        setLayout2ShowFigure();
    }

    private void setLayout2ShowFigure() {
        // TODO Auto-generated method stub
        FrameLayout layout = temperPiec;
        layout.removeAllViews();
        layout.setBackgroundColor(Color.alpha(0));
        layout.addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
    }

    // 生成一个数据源
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        series.add("差", values[0]);
        series.add("不达标", values[1]);
        series.add("达标", values[2]);
        series.add("优秀",values[3]);
        return series;
    }

    // 生成一个图表渲染参数
    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();

        renderer.setLegendTextSize(40);//设置左下角表注的文字大小

        //renderer.setZoomButtonsVisible(true);//设置显示放大缩小按钮
        renderer.setZoomEnabled(false);//设置不允许放大缩小.
        renderer.setChartTitleTextSize(15);//设置图表标题的文字大小
        renderer.setChartTitle("统计结果");//设置图表的标题  默认是居中顶部显示
        renderer.setShowLegend(false);//图列
        renderer.setLabelsTextSize(20);//饼图上标记文字的字体大小
        //renderer.setLabelsColor(Color.WHITE);//饼图上标记文字的颜色
        renderer.setPanEnabled(false);//设置是否可以平移
        renderer.setDisplayValues(true);//是否显示值
        renderer.setClickEnabled(false);//设置是否可以被点击
        renderer.setMargins(new int[] { 20, 30, 15,0 });
        //margins - an array containing the margin size values, in this order: top, left, bottom, right
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
    */

    private void createColumnar() {
        //背景颜色
        airBarc.setBackgroundColor(Color.WHITE);
        //网格
        airBarc.setDrawGridBackground(false);
        //背景阴影
        airBarc.setDrawBarShadow(false);
        airBarc.setHighlightEnabled(true);
        //显示边界
        airBarc.setDrawBorders(false);
        // 如果设置为true，所有值都高于其 bar 的,默认true
        airBarc.setDrawValueAboveBar(true);
        //如果设置为true，会在各条 bar 后面绘制 “灰色全 bar”,默认false
        airBarc.setDrawBarShadow(false);
        //图列
        airBarc.setDoubleTapToZoomEnabled(false);
        Legend legend = airBarc.getLegend();
        legend.setForm(Legend.LegendForm.LINE);//方形
        legend.setTextSize(11f);
        legend.setEnabled(false);//不显示图例
        airBarc.setDescription("");
        airBarc.setNoDataText("没有数据");
        XAxis xAxis = airBarc.getXAxis();
        xAxis.setDrawGridLines(false);
        YAxis left = airBarc.getAxisLeft();
        YAxis right = airBarc.getAxisRight();
        left.setDrawGridLines(true);
        left.setEnabled(true);
        left.setAxisMinValue(0);
        left.setAxisMaxValue(108);
        left.setDrawAxisLine(false);
        left.setLabelCount(7, true);
        right.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList<String> xValue = new ArrayList<String>();

        ArrayList<BarEntry> yValue = new ArrayList<BarEntry>();
        yValue.add(new BarEntry(87, 0));
        yValue.add(new BarEntry(91, 1));
        yValue.add(new BarEntry(94, 2));
        yValue.add(new BarEntry(103, 3));
        yValue.add(new BarEntry(105, 4));
        yValue.add(new BarEntry(102, 5));
        yValue.add(new BarEntry(102, 6));
        yValue.add(new BarEntry(101, 7));
        yValue.add(new BarEntry(87, 8));
        yValue.add(new BarEntry(91, 9));
        yValue.add(new BarEntry(94, 10));
        yValue.add(new BarEntry(103, 11));
        yValue.add(new BarEntry(105, 12));
        yValue.add(new BarEntry(102, 13));
        yValue.add(new BarEntry(102, 14));
        yValue.add(new BarEntry(101, 15));
        yValue.add(new BarEntry(87, 16));
        yValue.add(new BarEntry(91, 17));
        yValue.add(new BarEntry(94, 18));
        yValue.add(new BarEntry(103, 19));
        for (int i = 3; i < (yValue.size() + 1) * 3; i = i + 3) {
            xValue.add("" + i);
        }
        for (int i = 1; i < (yValue.size() * 2) / 3; i = i + 3) {
            xValue.add("");
        }
        xValue.add("(S)");
        xAxis.setValueFormatter(new XAxisValueFormatter() {
            @Override
            public String getXValue(String s, int i, ViewPortHandler viewPortHandler) {
                if (i < 3) {
                    s = "0" + s;
                }
                return s;
            }
        });
        BarDataSet barDataSet = new BarDataSet(yValue, "X轴");
        barDataSet.setValueTextSize(9f);
        barDataSet.setBarSpacePercent(50f);//间距
        barDataSet.setDrawValues(false);//柱状图上的值
        BarData barData = new BarData(xValue, barDataSet);
        airBarc.setData(barData);
        airBarc.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                airWeather.setText("过去一分钟的最差值："+entry.getVal());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        airBarc.setMarkerView(new CustomMarkerView(getContext(), R.layout.fragment_6_text));
        //airBarc.setBorderWidth(15f);
        airBarc.invalidate();

    }

    private void createPolyLine() {
        lc.setDescription("");
        lc.setNoDataText("没有数据");
        lc.setDrawGridBackground(false);
        lc.setDrawBorders(false);
        Legend legend = lc.getLegend();
        legend.setEnabled(false);
        XAxis xAxis = lc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setTextSize(21f);
        xAxis.setEnabled(true);
        xAxis.setDrawLabels(true);
        xAxis.setAvoidFirstLastClipping(true);
        YAxis leftYAxi = lc.getAxisLeft();
        YAxis rightYAxi = lc.getAxisRight();
        leftYAxi.setEnabled(false);
        leftYAxi.setAxisMaxValue(26.6f);
        leftYAxi.setAxisMinValue(13.9f);
        leftYAxi.setLabelCount(6, true);
        leftYAxi.setStartAtZero(false);
        rightYAxi.setEnabled(false);
        rightYAxi.setAxisMaxValue(26.6f);
        rightYAxi.setAxisMinValue(13.9f);
        rightYAxi.setLabelCount(6, true);
        lc.invalidate();
        ArrayList<Entry> up = new ArrayList<Entry>();
        ArrayList<Entry> down = new ArrayList<Entry>();
        up.add(new Entry(22, 0));
        up.add(new Entry(24, 1));
        up.add(new Entry(25, 2));
        up.add(new Entry(25, 3));
        up.add(new Entry(25, 4));
        up.add(new Entry(22, 5));
        down.add(new Entry(14, 0));
        down.add(new Entry(15, 1));
        down.add(new Entry(16, 2));
        down.add(new Entry(17, 3));
        down.add(new Entry(16, 4));
        down.add(new Entry(16, 5));
        LineDataSet setup = new LineDataSet(up, "UP");
        LineDataSet setdown = new LineDataSet(down, "DOWN");
        initLineDataSet(setup, Color.RED, false);
        initLineDataSet(setdown, Color.BLUE, false);
        setup.setAxisDependency(YAxis.AxisDependency.LEFT);
        setdown.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<LineDataSet> arrayList = new ArrayList<LineDataSet>();
        arrayList.add(setup);
        arrayList.add(setdown);
        //x轴内容名字
        String[] week1 = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] week2 = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.CHINESE);
        String str = simpleDateFormat.format(System.currentTimeMillis());
        int i = 0;
        while (!str.equals(week2[i]))
            i++;
        Log.e("xxx", str + " " + i);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("昨天");
        xVals.add("今天");
        xVals.add("明天");
        //TODO 后面具体的星期几,根据系统日期计算
        //这里展示的是图片当前内容的
        xVals.add(week1[(i + 2) % 7]);
        xVals.add(week1[(i + 3) % 7]);
        xVals.add(week1[(i + 4) % 7]);
        LineData lineData = new LineData(xVals, arrayList);
        lc.setData(lineData);
        lc.invalidate();

    }

    private void initLineDataSet(LineDataSet lineDataSet, int color, boolean mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleSize(6f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);//实心
        lineDataSet.setDrawValues(true);// 是否在点上绘制Value
        lineDataSet.setValueTextSize(9f);

        //设置高亮
        lineDataSet.setHighlightEnabled(true);

        //设置折线图填充
        lineDataSet.setDrawFilled(mode);
        /*lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);*/
        //线模式为圆滑曲线（默认折线）
        /*lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);*/
    }

    private void initView(View view) {
        ziwaixiang1 = (TextView) view.findViewById(R.id.ziwaixiang1);
        ziwaixiang2 = (TextView) view.findViewById(R.id.ziwaixiang2);
        ganmao1 = (TextView) view.findViewById(R.id.ganmao1);
        ganmao2 = (TextView) view.findViewById(R.id.ganmao2);
        chuanyi1 = (TextView) view.findViewById(R.id.chuanyi1);
        chuanyi2 = (TextView) view.findViewById(R.id.chuanyi2);
        yundong1 = (TextView) view.findViewById(R.id.yundong1);
        yundong2 = (TextView) view.findViewById(R.id.yundong2);
        kongqi1 = (TextView) view.findViewById(R.id.kongqi1);
        kongqi2 = (TextView) view.findViewById(R.id.kongqi2);
        tvair=(TextView) view.findViewById(R.id.tvair);
        tvtoday= (TextView) view.findViewById(R.id.tvtoday);
    }
}
