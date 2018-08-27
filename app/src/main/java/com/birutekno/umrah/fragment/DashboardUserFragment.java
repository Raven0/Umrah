package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.birutekno.umrah.JamaahActivity;
import com.birutekno.umrah.KalkulasiActivity;
import com.birutekno.umrah.PotkomActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.helper.ChartResponse;
import com.birutekno.umrah.helper.PeriodeResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.model.DataPeriode;
import com.birutekno.umrah.ui.chart.LineView;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by No Name on 7/29/2017.
 */

public class DashboardUserFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";
    private static final String TYPE = "type";

    private ProgressDialog nDialog;
    private ProgressDialog aDialog;
    private ProgressDialog bDialog;

    int randomint = 12;

    @Bind(R.id.line_view)
    LineView line;

    @Bind(R.id.line_view_two)
    LineView line_two;

    @Bind(R.id.periode)
    Spinner periode;

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

    private ArrayList<DataPeriode> pojd;
    List<String> listPeriode = new ArrayList<String>();

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
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final String id_agen = prefs.getString("iduser", "0");
        final String token = prefs.getString("token", "0");

        loadPeriode();
        initLineView(line);
        initLineView(line_two);

        loadDataProspek(String.valueOf(id_agen));

        periode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                nDialog = ProgressDialog.show(getContext(), null, "Memuat Data...", true, false);
                nDialog.show();
                try {
                    loadDataPotensi(String.valueOf(id_agen), item);
                    loadDataKomisi(String.valueOf(id_agen), item);
                    loadDataJamaah(String.valueOf(id_agen), item);
                    setDataTotalJamaahView(id_agen, item);
                    setDataTotalPotkomView(id_agen, item);
                    nDialog.dismiss();
                }catch (Exception ex){
                    nDialog.dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                try {
                    loadDataPotensi(String.valueOf(id_agen), token);
                    loadDataKomisi(String.valueOf(id_agen), token);
                    loadDataJamaah(String.valueOf(id_agen), token);
                    setDataTotalJamaahView(id_agen, token);
                    setDataTotalPotkomView(id_agen, token);
                    nDialog.dismiss();
                }catch (Exception ex){
                    nDialog.dismiss();
                }
            }
        });

        prospekCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KalkulasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });

        jamaahCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JamaahActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });

        potensiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PotkomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("viewpager_position", 1);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });

        komisiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PotkomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });
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

    private void setDataTotalJamaahView(final String id, final String tahun){
        aDialog = ProgressDialog.show(getContext(), null, "Memuat Data Grafik...", true, false);
        aDialog.show();
        Call<ChartResponse> call = WebApi.getAPIService().getJamaahPerbulan(id, tahun);
        call.enqueue(new Callback<ChartResponse>() {
            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
                try{
                    //Grafik Total Jamaah
                    //Value
                    ArrayList<Integer> dataList = new ArrayList<>();
                    dataList.add(Integer.parseInt(response.body().getResponse().getJanuary()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getFebruary()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getMarch()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getApril()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getMei()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getJune()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getJuly()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getAugust()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getSeptember()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getOctober()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getNovember()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getDecember()));

                    //Assign to Array
                    ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
                    dataLists.add(dataList);

                    //Set data to graph
                    line.setDataList(dataLists);

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
                aDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
                setDataTotalJamaahView(id, tahun);
            }
        });
    }

    private void setDataTotalPotkomView(final String id, final String tahun){
        bDialog = ProgressDialog.show(getContext(), null, "Memuat Data Grafik...", true, false);
        bDialog.show();
        Call<ChartResponse> call = WebApi.getAPIService().getKomisiPerbulan(id, tahun);
        call.enqueue(new Callback<ChartResponse>() {
            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
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
                    //Grafik Total Jamaah
                    //Value
                    ArrayList<Integer> dataList = new ArrayList<>();
                    dataList.add(Integer.parseInt(response.body().getResponse().getJanuary()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getFebruary()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getMarch()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getApril()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getMei()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getJune()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getJuly()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getAugust()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getSeptember()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getOctober()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getNovember()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getDecember()));

                    //Assign to Array
                    ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
                    dataLists.add(dataList);

                    //Set data to graph
                    line_two.setDataList(dataLists);

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
                bDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
                setDataTotalJamaahView(id, tahun);
            }
        });
        //Grafik Total PotKom
        //Value Komisi
//        ArrayList<Integer> dataListF = new ArrayList<>();
//        dataListF.add(100);
//        dataListF.add(80);
//        dataListF.add(210);
//        dataListF.add(200);
//        dataListF.add(220);
//        dataListF.add(220);
//        dataListF.add(240);
//        dataListF.add(350);
//        dataListF.add(400);
//        dataListF.add(340);
//        dataListF.add(420);
//        dataListF.add(500);
//
//        //Value Potensi
//        ArrayList<Integer> dataListF2 = new ArrayList<>();
//        dataListF2.add(100);
//        dataListF2.add(80);
//        dataListF2.add(210);
//        dataListF2.add(200);
//        dataListF2.add(220);
//        dataListF2.add(220);
//        dataListF2.add(240);
//        dataListF2.add(350);
//        dataListF2.add(400);
//        dataListF2.add(340);
//        dataListF2.add(420);
//        dataListF2.add(500);
//
//        //Assign To Array
//        ArrayList<ArrayList<Integer>> dataListFs = new ArrayList<>();
//        dataListFs.add(dataListF);
//        dataListFs.add(dataListF2);
//
//        //Set Data to graph
//        line_two.setDataList(dataListFs);
    }

    private void loadDataPotensi(final String id, final String tahun){
        progressPotensi.setVisibility(View.VISIBLE);
        potensi.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getUangPotensi(id, tahun);
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
                loadDataPotensi(id, tahun);
            }
        });
    }

    private void loadDataKomisi(final String id, final String tahun){
        progressKomisi.setVisibility(View.VISIBLE);
        komisi.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getUangKomisi(id, tahun);
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
                loadDataKomisi(id, tahun);
            }
        });
    }

    private void loadDataJamaah(final String id, final String tahun){
        progressJamaah.setVisibility(View.VISIBLE);
        jamaah.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getTotalJamaah(id, tahun);
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
                loadDataJamaah(id, tahun);
            }
        });
    }

    private void loadDataProspek(final String id){
        progressProspek.setVisibility(View.VISIBLE);
        prospek.setVisibility(View.GONE);
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

    private void loadPeriode(){
        Call<PeriodeResponse> call = WebApi.getAPIService().getPeriode();
        call.enqueue(new Callback<PeriodeResponse>() {
            @Override
            public void onResponse(Call<PeriodeResponse> call, Response<PeriodeResponse> response) {
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
                    PeriodeResponse PeriodeResponse = response.body();
                    pojd = new ArrayList<>(Arrays.asList(PeriodeResponse.getData()));
                    for (int i = 0; i < pojd.size() ; i++ ){
                        listPeriode.add(pojd.get(i).getJudul());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, listPeriode);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    periode.setAdapter(adapter);
                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<PeriodeResponse> call, Throwable t) {
                loadPeriode();
            }
        });
    }

    public String getFirstFour(String args) {
        String temp = args;
        String output = temp.substring(temp.length() + 4);
        return output;
    }
}