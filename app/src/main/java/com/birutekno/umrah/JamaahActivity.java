package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.birutekno.umrah.adapter.AbsensiAdapter;
import com.birutekno.umrah.helper.Shortcuts;
import com.birutekno.umrah.ui.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 7/31/2017.
 */

public class JamaahActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.date)
    TextView mDatePicker;

    @OnClick(R.id.date)
    public void date() {
        showCalendar();
    }

    private AbsensiAdapter mAdapter;
    private String mDate = "";

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JamaahActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_jamaah;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        String[] ITEMS = {"L", "P"};
        String[] ITEM = {"9 HARI", "12 HARI"};
        ArrayAdapter<String> adapterK = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        ArrayAdapter<String> adapterP = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEM);
        adapterK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
//        MaterialSpinner spinner1 = (MaterialSpinner) findViewById(R.id.spinnerPaket);
//        spinner.setAdapter(adapterK);
//        spinner1.setAdapter(adapterP);

        setUpAdapter();
    }

    private void setUpAdapter() {
        mAdapter = new AbsensiAdapter(mContext);
    }

    private void showCalendar() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                JamaahActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "DATE");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = String.valueOf(monthOfYear + 1);
        if (monthOfYear + 1 < 10) {
            month = "0" + month;
        }
        String date = String.valueOf(dayOfMonth);
        if (dayOfMonth < 10) {
            date = "0" + date;
        }
        mDate = year + "-" + month + "-" + date;
        mDatePicker.setText(date + "-" + Shortcuts.getFullMonth(month) + "-" + year);
    }
}
