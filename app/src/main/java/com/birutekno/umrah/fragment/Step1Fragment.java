package com.birutekno.umrah.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.umrah.InputActivity;
import com.birutekno.umrah.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Step1Fragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText jumlah;
    private EditText editTextAlamat;
    private Button buttonNext;

    public Step1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_step1, container, false);
        loadComponent();
        return view;
    }

    private void loadComponent() {
        jumlah = (EditText) view.findViewById(R.id.jumlah);
//        editTextAlamat = (EditText) view.findViewById(R.id.edit_text_alamat);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_1);
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonNext) {
            if(jumlah.getText().toString().trim().length() == 0){
                Toast.makeText(getContext(), "Pastikan jumlah jamaah terisi", Toast.LENGTH_SHORT).show();
            }else {
                String input = jumlah.getText().toString().trim();
                int jml = Integer.parseInt(input);
                InputActivity.goToStepOrangTua();
                Step2Fragment step2Fragment = new Step2Fragment();
                Bundle bundle = new Bundle();
                bundle.putInt("jumlah", jml);
                bundle.putInt("cjumlah", 1);
                step2Fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layout, step2Fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
