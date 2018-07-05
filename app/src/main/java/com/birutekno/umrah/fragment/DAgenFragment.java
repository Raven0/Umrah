package com.birutekno.umrah.fragment;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.chart.LineView;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;

/**
 * Created by No Name on 7/29/2017.
 */

public class DAgenFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{

    ArrayList<String> items =new ArrayList<>();

    private static final String TYPE = "type";

    int randomint = 12;

    @Bind(R.id.line_view_two)
    LineView line_two;

    @Bind(R.id.spinner_search)
    Spinner spinner;

    @Bind(R.id.periode)
    Button periode;

    public static DAgenFragment newInstance(int type) {
        DAgenFragment fragment = new DAgenFragment();
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
        initLineView(line_two);

        periode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) DAgenFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih Periode");
                datePickerDialog.show(fm,"Date");
            }
        });

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

        items.add("Pilih Agen");
        items.add("Andika");
        items.add("Bayu");
        items.add("Depras");
        items.add("Devani");
        items.add("Dhafa");
        items.add("Dicka");
        items.add("Dinda");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case "Pilih Agen":
                        break;
                    case "Andika":
                        break;
                    case "Bayu":
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
}
