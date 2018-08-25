package com.birutekno.umrah.fragment;

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
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.helper.AgenResponse;
import com.birutekno.umrah.helper.ChartResponse;
import com.birutekno.umrah.helper.PeriodeResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.model.DataAgen;
import com.birutekno.umrah.model.DataPeriode;
import com.birutekno.umrah.ui.chart.LineView;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class DashboardAgenFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";
    String id_agen,token;

    private static final String TYPE = "type";
    int randomint = 12;

    @Bind(R.id.agenAlert)
    TextView agenAlert;

    @Bind(R.id.line_view_two)
    LineView line_two;

    @Bind(R.id.spinner_search)
    Spinner spinner;

    @Bind(R.id.periode)
    Spinner periode;

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

//    @Bind(R.id.progressJamaah)
//    ProgressBar progressJamaah;

    private ArrayList<DataAgen> pojo;
    private ArrayList<DataPeriode> pojd;
    List<String> listAgen = new ArrayList<String>();
    List<String> idAgen = new ArrayList<String>();
    List<String> listPeriode = new ArrayList<String>();

    String selectedid;
    String selectedperiode;

    boolean selected = false;

    public static DashboardAgenFragment newInstance(int type) {
        DashboardAgenFragment fragment = new DashboardAgenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_dashboard_agen;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState ) {

        progressProspek = (ProgressBar) getView().findViewById(R.id.progressProspek);
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        id_agen = prefs.getString("iduser", "0");
        token = prefs.getString("token", "0");

        loadPeriode();
        initLineView(line_two);
        loadAgen(id_agen);

        periode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedperiode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedperiode = parent.getItemAtPosition(0).toString();
                periode.setSelection(0);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedid = idAgen.get(position);
                loadDataJamaah(selectedid,selectedperiode);
                loadDataProspek(selectedid);
                setDataKomisiView(selectedid,selectedperiode);
                selected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadDataJamaah(idAgen.get(0),token);
                loadDataProspek(idAgen.get(0));
                setDataKomisiView(idAgen.get(0),token);
                spinner.setSelection(0);
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

    private void setDataKomisiView(final String id, final String tahun){
        Call<ChartResponse> call = WebApi.getAPIService().getKomisiAgenPerbulan(id, tahun);
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
            }
            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
                setDataKomisiView(id, tahun);
            }
        });
    }

    private void loadDataJamaah(final String id, final String tahun){
//        progressJamaah.setVisibility(View.VISIBLE);
//        jamaah.setVisibility(View.GONE);
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
//                    progressJamaah.setVisibility(View.GONE);
//                    jamaah.setVisibility(View.VISIBLE);
                    jamaah.setText(response.body().getResponse().getTotal());

                }catch (Exception ex){
                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void loadAgen(String id){
        Call<AgenResponse> call = WebApi.getAPIService().getSubAgen(id);
        call.enqueue(new Callback<AgenResponse>() {
            @Override
            public void onResponse(Call<AgenResponse> call, Response<AgenResponse> response) {
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
                    AgenResponse agenResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(agenResponse.getAgen()));
                    for (int i = 0; i < pojo.size() ; i++ ){
                        listAgen.add(pojo.get(i).getNama());
                        idAgen.add(pojo.get(i).getId());
                    }
                    if (listAgen.size()!=0){
                        ArrayAdapter<String> adapters = new ArrayAdapter<String>(mContext,
                                android.R.layout.simple_spinner_item, listAgen);
                        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapters);
                        spinner.setVisibility(View.VISIBLE);
                        agenAlert.setVisibility(View.GONE);
                    }else {
                        spinner.setVisibility(View.GONE);
                        agenAlert.setVisibility(View.VISIBLE);
                    }

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<AgenResponse> call, Throwable t) {
                loadPeriode();
            }
        });
    }
}
