package com.birutekno.umrah.fragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.JamaahActivity;
import com.birutekno.umrah.KalkulasiActivity;
import com.birutekno.umrah.PotkomActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.ui.chart.LineView;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by No Name on 7/29/2017.
 */

public class DashboardUserFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

    public static final String PREFS_NAME = "AUTH";
    private static final String TYPE = "type";

    private ProgressDialog nDialog;

    int randomint = 12;

    @Bind(R.id.line_view)
    LineView line;

    @Bind(R.id.line_view_two)
    LineView line_two;

    @Bind(R.id.periode)
    Button periode;

    @Bind(R.id.potensiCard)
    CardView potensiCard;

    @Bind(R.id.potensi)
    TextView potensi;

    @Bind(R.id.progressPotensi)
    ProgressBar progressPotensi;

    @Bind(R.id.komisiCard)
    CardView komisiCard;

    @Bind(R.id.komisi)
    TextView komisi;

    @Bind(R.id.progressKomisi)
    ProgressBar progressKomisi;

    @Bind(R.id.prospekCard)
    CardView prospekCard;

    @Bind(R.id.prospek)
    TextView prospek;

    @Bind(R.id.progressProspek)
    ProgressBar progressProspek;

    @Bind(R.id.jamaahCard)
    CardView jamaahCard;

    @Bind(R.id.jamaah)
    TextView jamaah;

    @Bind(R.id.progressJamaah)
    ProgressBar progressJamaah;

    public static DashboardUserFragment newInstance(int type) {
        DashboardUserFragment fragment = new DashboardUserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static DashboardUserFragment newInstance() {
        DashboardUserFragment fragment = new DashboardUserFragment();
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

        periode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) DashboardUserFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih Periode");
                datePickerDialog.show(fm,"Date");
            }
        });

        prospekCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KalkulasiActivity.class);
                startActivity(intent);
            }
        });

        jamaahCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JamaahActivity.class);
                startActivity(intent);
            }
        });

        potensiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PotkomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("viewpager_position", 1);
                startActivity(intent);
            }
        });

        komisiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PotkomActivity.class);
                startActivity(intent);
            }
        });

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

        nDialog = ProgressDialog.show(getContext(), null, "Memuat Data...", true, false);
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String id_agen = prefs.getString("iduser", "0");
        nDialog.show();
        try {
            loadDataPotensi(String.valueOf(id_agen));
            loadDataKomisi(String.valueOf(id_agen));
            loadDataJamaah(String.valueOf(id_agen));
            loadDataProspek(String.valueOf(id_agen));
            nDialog.dismiss();
        }catch (Exception ex){
            nDialog.dismiss();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int day){
        Toast.makeText(getContext(), String.format("You Selected : %d/%d/%d", day,month,year), Toast.LENGTH_LONG).show();
        periode.setText(String.format("%d/%d/%d", day,month,year));
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
        lineView.setColorArray(new int[]{Color.BLUE,Color.RED});
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void loadDataPotensi(final String id){
        Call<DashboardModel> call = WebApi.getAPIService().getUangPotensi(id);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    progressPotensi.setVisibility(View.GONE);
                    potensi.setVisibility(View.VISIBLE);
                    potensi.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    potensi.setText("Rp. " + numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataPotensi(id);
            }
        });
    }

    private void loadDataKomisi(final String id){
        Call<DashboardModel> call = WebApi.getAPIService().getUangKomisi(id);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    progressKomisi.setVisibility(View.GONE);
                    komisi.setVisibility(View.VISIBLE);
                    komisi.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    komisi.setText(numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataKomisi(id);
            }
        });
    }

    private void loadDataJamaah(final String id){
        Call<DashboardModel> call = WebApi.getAPIService().getTotalJamaah(id);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    progressJamaah.setVisibility(View.GONE);
                    jamaah.setVisibility(View.VISIBLE);
                    jamaah.setText(response.body().getResponse().getTotal());

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataJamaah(id);
            }
        });
    }

    private void loadDataProspek(final String id){
        Call<DashboardModel> call = WebApi.getAPIService().getTotalProspek(id);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    progressProspek.setVisibility(View.GONE);
                    prospek.setVisibility(View.VISIBLE);
                    prospek.setText(response.body().getResponse().getTotal());

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataProspek(id);
            }
        });
    }

    public String numberFormat(String args){
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(args);
        String formattedNumber = formatter.format(myNumber);
        return formattedNumber;
    }

    public String getFirstFour(String args){
        String temp = args;
        String output = temp.substring(temp.length()+4);
        return output;
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
