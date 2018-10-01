package com.birutekno.aiwa.fragment;

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

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.helper.AgenResponse;
import com.birutekno.aiwa.helper.ChartResponse;
import com.birutekno.aiwa.helper.PeriodeResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DashboardModel;
import com.birutekno.aiwa.model.DataAgen;
import com.birutekno.aiwa.model.DataPeriode;
import com.birutekno.aiwa.ui.chart.LineView;
import com.birutekno.aiwa.ui.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

public class DashboardAgenFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";
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

        initLineView(line_two);
        loadAgen(id_agen);

        loadOnLoad();

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
                    dataList.add(removeLastThree(response.body().getResponse().getJanuary()));
                    dataList.add(removeLastThree(response.body().getResponse().getFebruary()));
                    dataList.add(removeLastThree(response.body().getResponse().getMarch()));
                    dataList.add(removeLastThree(response.body().getResponse().getApril()));
                    dataList.add(removeLastThree(response.body().getResponse().getMei()));
                    dataList.add(removeLastThree(response.body().getResponse().getJune()));
                    dataList.add(removeLastThree(response.body().getResponse().getJuly()));
                    dataList.add(removeLastThree(response.body().getResponse().getAugust()));
                    dataList.add(removeLastThree(response.body().getResponse().getSeptember()));
                    dataList.add(removeLastThree(response.body().getResponse().getOctober()));
                    dataList.add(removeLastThree(response.body().getResponse().getNovember()));
                    dataList.add(removeLastThree(response.body().getResponse().getDecember()));

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
//                setDataKomisiView(id, tahun);
            }
        });
    }

    private void loadDataJamaah(final String id, final String tahun){
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
                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
//                loadDataJamaah(id, tahun);
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
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
//                loadDataProspek(id);
                Log.d("PROSPEK AGEN", "onFailure: ");
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadPeriode(final ArrayList<DataPeriode> cache){
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

                    SharedPreferences.Editor editor = getContext().getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(pojd);
                    editor.putString("pojo_periode",json);
                    editor.apply();

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
                loadPeriodeCache(cache);
            }
        });
    }

    private void loadPeriodeCache(ArrayList<DataPeriode> cache){
        try {
            for (int i = 0; i < cache.size() ; i++ ){
                listPeriode.add(cache.get(i).getJudul());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listPeriode);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            periode.setAdapter(adapter);
        }catch (Exception ex){
            loadPeriode(cache);
            Log.d("FAILCACHE", "onItemSelected: " + ex.getMessage());
        }
    }

    public void loadOnLoad(){
        SharedPreferences prefs = DashboardAgenFragment.this.getContext().getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("pojo_periode", "");
        Type type = new TypeToken<ArrayList<DataPeriode>>(){}.getType();
        ArrayList<DataPeriode> dataPeriodes = gson.fromJson(json, type);
        loadPeriode(dataPeriodes);
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
//                loadPeriode();
                Log.d("LOAD AGEN", "onFailure: ");
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    public int removeLastThree(String args) {
        int output = 0;
        if (args.length() > 3){
            output = Integer.parseInt(args.substring(0, args.length() - 3));
        }else {
            output = Integer.parseInt(args);
        }
        return output;
    }
}
