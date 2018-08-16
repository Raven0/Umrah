package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.AgenObject;
import com.birutekno.umrah.model.DataAgen;
import com.birutekno.umrah.ui.BaseActivity;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class EditProfileActivity extends BaseActivity implements View.OnClickListener{

    public static final String PREFS_NAME = "AUTH";
    private ProgressDialog pDialog;

    public Toolbar mToolbar;
    public Button btnEdt;
    EditText ktp;
    EditText nama;
    EditText notelp;
    String email;
    String username;
    String status;
    String koordinator;
    EditText domisili;
    EditText bank;
    EditText norek;
    EditText narek;
    EditText feereg;
    EditText feeprom;
    EditText web;
    CircleImageView imgView;
    RadioButton rb1, rb2;

    private String jenis_kelamin;
    private Boolean jk;

    private Uri uri;
    private Uri resultUri;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btnEdt = (Button) findViewById(R.id.btnEdit);
        btnEdt.setOnClickListener(this);
        ktp = (EditText) findViewById(R.id.noktp);
        nama = (EditText) findViewById(R.id.nama);
        notelp = (EditText) findViewById(R.id.telp);
//        email = (EditText) findViewById(R.id.email);
        domisili = (EditText) findViewById(R.id.alamat);
        bank = (EditText) findViewById(R.id.bank);
        norek = (EditText) findViewById(R.id.norek);
        narek = (EditText) findViewById(R.id.narek);
        feereg = (EditText) findViewById(R.id.feereg);
        feeprom = (EditText) findViewById(R.id.feepromo);
        web = (EditText) findViewById(R.id.website);
        imgView = (CircleImageView) findViewById(R.id.imgView);
        rb1 = (RadioButton) findViewById(R.id.pria);
        rb2 = (RadioButton) findViewById(R.id.wanita);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);
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
    protected void onViewReady(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");
        loadData(String.valueOf(id));
    }

    private void loadData(final String id){

        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<AgenObject> call = WebApi.getAPIService().showAgen(id);
        call.enqueue(new Callback<AgenObject>() {
            @Override
            public void onResponse(Call<AgenObject> call, Response<AgenObject> response) {
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
                    DataAgen dataAgen = response.body().getData();

                    String link = dataAgen.getFoto();
                    Picasso.get().load(link).fit().centerCrop().into(imgView);
                    ktp.setText(dataAgen.getNo_ktp());
                    nama.setText(dataAgen.getNama());
                    notelp.setText(dataAgen.getNo_telp());
                    email = dataAgen.getEmail();
                    username = dataAgen.getUsername();
                    status = dataAgen.getStatus();
                    koordinator = dataAgen.getKoordinator();
                    domisili.setText(dataAgen.getAlamat());
                    jenis_kelamin = dataAgen.getJenis_kelamin();
                    if (jenis_kelamin.equals("L")){
                        rb1.setChecked(true);
                        rb2.setChecked(false);
                    }else {
                        rb1.setChecked(false);
                        rb2.setChecked(true);
                    }
                    bank.setText(dataAgen.getBank());
                    norek.setText(dataAgen.getNo_rekening());
                    narek.setText(dataAgen.getNama_rek_beda());
                    feereg.setText(dataAgen.getFee_reguler());
                    feeprom.setText(dataAgen.getFee_promo());
                    web.setText(dataAgen.getWebsite());
                    pDialog.dismiss();
                }catch (Exception ex){
                    pDialog.dismiss();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<AgenObject> call, Throwable t) {
                loadData(id);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnEdt){
            SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String id_agen = prefs.getString("iduser", "0");
            String password = prefs.getString("password", "123");

            HashMap<String, String> params = new HashMap<>();

            if(rb1.isChecked()){
                jenis_kelamin = "L";
                jk = true;
            }else if(rb2.isChecked()){
                jenis_kelamin = "P";
                jk = true;
            }else {
                jk = false;
            }

            //anggota_id
            if (jk){
                params.put("nama", nama.getText().toString());
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                params.put("jenis_kelamin", jenis_kelamin);
                params.put("no_ktp", ktp.getText().toString());
                params.put("alamat", domisili.getText().toString());
                params.put("no_telp", notelp.getText().toString());
                params.put("bank", bank.getText().toString());
                params.put("status", status);
                params.put("koordinator", koordinator);
                params.put("no_rekening", norek.getText().toString());
                params.put("fee_reguler", feereg.getText().toString());
                params.put("fee_promo", feeprom.getText().toString());
                params.put("nama_rek_beda", narek.getText().toString());
                params.put("website", web.getText().toString());

                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage("Harap tunggu...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<ResponseBody> result = WebApi.getAPIService().editAgen(String.valueOf(id_agen), params);
                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pDialog.dismiss();
                        try {
                            if(response.body()!=null){
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(mContext, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "Error Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pDialog.dismiss();
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(mContext, "On Failure", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }else {
                Toast.makeText(mContext, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

            uri = data.getData();

            CropImage.activity(uri)
                    .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String id_agen = prefs.getString("iduser", "0");

                resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage("Harap tunggu...");
                pDialog.setCancelable(false);
                pDialog.show();

                retrofit2.Call<okhttp3.ResponseBody> req = WebApi.getAPIService().photoAgen(id_agen, body, name);
                req.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // Do Something
                        imgView.setImageURI(resultUri);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("ERROR", "onFailure: " + t.getMessage());
                    }
                });

                pDialog.dismiss();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
