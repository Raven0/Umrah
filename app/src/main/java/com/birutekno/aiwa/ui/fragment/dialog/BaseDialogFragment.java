package com.birutekno.aiwa.ui.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.birutekno.aiwa.ui.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created on : 05-03-2016
 * Author     : derohimat
 * Name       : Deni Rohimat
 * Email      : rohimatdeni@gmail.com
 * GitHub     : https://github.com/derohimat
 * LinkedIn   : https://www.linkedin.com/in/derohimat
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected Context mContext;
    protected LayoutInflater mInflater;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = mInflater.inflate(getResourceLayout(), null);
        ButterKnife.bind(this, view);
        return setupDialog(view);
    }

    protected Dialog setupDialog(View view) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x4000));
        return dialog;
    }

    protected abstract int getResourceLayout();

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
