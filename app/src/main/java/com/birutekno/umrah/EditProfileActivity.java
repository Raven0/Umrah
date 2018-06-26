package com.birutekno.umrah;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.birutekno.umrah.ui.BaseActivity;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class EditProfileActivity extends BaseActivity {

    public Toolbar mToolbar;
    private ImageView imgCancel;
    CircleImageView img;
    private final int select_photo = 1; // request code fot gallery intent
    private static Uri imageUri = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                EditProfileActivity.super.onBackPressed();
            }
        });

        img = (CircleImageView) findViewById(R.id.imgView);
        imgCancel = (ImageView) findViewById(R.id.imgBtnCancel);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int PERMISSION_ALL = 1;
                String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};

                if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(EditProfileActivity.this, PERMISSIONS, PERMISSION_ALL);
                }

                else {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "IMAGE " + new Date().getTime() + ".jpg");
                    chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    if (chooserIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(chooserIntent, 1);
                    }
//                startActivityForResult(chooserIntent, CAM_REQ_CODE);
                }
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                img.setImageDrawable(getResources().getDrawable(R.drawable.border_single_mr_mrs));
            }
        });
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) { }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onActivityResult(int requestcode, int resultcode, Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case select_photo:
                if (resultcode == RESULT_OK) {

                    imageUri = imagereturnintent.getData();// Get intent
                    img.setImageURI(imageUri);
                    // bitmap
                    img.setVisibility(View.VISIBLE);// Visible button
                }
        }
    }
}
