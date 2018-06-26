package com.birutekno.umrah.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.chart.LineView;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;

/**
 * Created by No Name on 7/29/2017.
 */

public class DUserFragment extends BaseFragment{

    private static final String TYPE = "type";

    int randomint = 12;

    @Bind(R.id.line_view)
    LineView line;

    @Bind(R.id.line_view_two)
    LineView line_two;

    public static DUserFragment newInstance(int type) {
        DUserFragment fragment = new DUserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static DUserFragment newInstance() {
        DUserFragment fragment = new DUserFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_dashboard_user;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initLineView(line);
        initLineView(line_two);

        ArrayList<Integer> dataList = new ArrayList<>();
        dataList.add(100);
        dataList.add(80);
        dataList.add(210);
        dataList.add(200);
        dataList.add(220);
        dataList.add(220);
        dataList.add(240);
        dataList.add(350);
        dataList.add(400);
        dataList.add(340);
        dataList.add(420);
        dataList.add(500);

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList);

        line.setDataList(dataLists);

        ArrayList<Float> dataListF = new ArrayList<>();
        float randomF = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF2 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF2.add((float) (Math.random() * randomF));
        }

        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
        dataListFs.add(dataListF);
        dataListFs.add(dataListF2);

        line_two.setFloatDataList(dataListFs);

//        randomSet(line,line_two);
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < randomint; i++) {
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            calendar.set(Calendar.MONTH, i);
            String month_name = month_date.format(calendar.getTime());
            test.add(month_name);
        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[]{Color.GREEN,Color.RED});
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

//    private void randomSet(LineView lineView, LineView lineViewTwo) {
//        ArrayList<Integer> dataList = new ArrayList<>();
//        float random = (float) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataList.add((int) (Math.random() * random));
//        }
//
//        ArrayList<Integer> dataList2 = new ArrayList<>();
//        random = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataList2.add((int) (Math.random() * random));
//        }
//
//        ArrayList<Integer> dataList3 = new ArrayList<>();
//        random = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataList3.add((int) (Math.random() * random));
//        }
//
//        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
//        dataLists.add(dataList);
//
//        lineView.setDataList(dataLists);
//
//        ArrayList<Float> dataListF = new ArrayList<>();
//        float randomF = (float) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<Float> dataListF2 = new ArrayList<>();
//        randomF = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF2.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<Float> dataListF3 = new ArrayList<>();
//        randomF = (int) (Math.random() * 9 + 1);
//        for (int i = 0; i < randomint; i++) {
//            dataListF3.add((float) (Math.random() * randomF));
//        }
//
//        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
//        dataListFs.add(dataListF);
//        dataListFs.add(dataListF2);
//
//        lineViewTwo.setFloatDataList(dataListFs);
//    }
}
