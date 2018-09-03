package com.birutekno.umrah.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.EditKalkulasiActivity;
import com.birutekno.umrah.KalkulasiActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataProspek;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by No Name on 7/31/2017.
 */

public class KalkulasiAdapter extends RecyclerView.Adapter<KalkulasiAdapter.ViewHolder> implements Filterable {

    public static final String PREFS_NAME = "AUTH";
    public ArrayList<DataProspek> dataProspeks;
    public ArrayList<DataProspek> mFilterData;
    private ProgressDialog pDialog;
    Context context;

    public KalkulasiAdapter(ArrayList<DataProspek> dataProspeks, Context context) {
        this.dataProspeks = dataProspeks;
        mFilterData = dataProspeks;
        this.context = context;
    }

    @Override
    public KalkulasiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_kalkulasi, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KalkulasiAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(mFilterData.get(i).getId());
        viewHolder.nama.setText(mFilterData.get(i).getPic());
        viewHolder.telp.setText(mFilterData.get(i).getNo_telp());

        viewHolder.pic = mFilterData.get(i).getPic();
        viewHolder.no_telp = mFilterData.get(i).getNo_telp();
        viewHolder.jml_dewasa = mFilterData.get(i).getJml_dewasa();
        viewHolder.jml_infant= mFilterData.get(i).getJml_infant();
        viewHolder.jml_balita= mFilterData.get(i).getJml_balita();
        viewHolder.jml_visa = mFilterData.get(i).getJml_visa();
        viewHolder.jml_balita_kasur = mFilterData.get(i).getJml_balita_kasur();
        viewHolder.tgl_keberangkatan = mFilterData.get(i).getTgl_keberangkatan();
        viewHolder.jenis = mFilterData.get(i).getJenis();
        viewHolder.dobel = mFilterData.get(i).getDobel();
        viewHolder.triple = mFilterData.get(i).getTriple();
        viewHolder.quard = mFilterData.get(i).getQuard();
        viewHolder.passport = mFilterData.get(i).getPassport();
        viewHolder.meningitis = mFilterData.get(i).getMeningitis();
        viewHolder.pas_foto = mFilterData.get(i).getPas_foto();
        viewHolder.buku_nikah = mFilterData.get(i).getBuku_nikah();
        viewHolder.fc_akta = mFilterData.get(i).getFc_akta();
        viewHolder.visa_progresif = mFilterData.get(i).getVisa_progresif();
        viewHolder.diskon = mFilterData.get(i).getDiskon();
        viewHolder.keterangan = mFilterData.get(i).getKeterangan();
        viewHolder.tanggal_followup = mFilterData.get(i).getTanggal_followup();
        viewHolder.perlengkapan_balita = mFilterData.get(i).getPerlengkapan_balita();
        viewHolder.perlengkapan_dewasa = mFilterData.get(i).getPerlengkapan_dewasa();

        int jmldewasa = Integer.parseInt(mFilterData.get(i).getJml_dewasa());
        int jmlinfant = Integer.parseInt(mFilterData.get(i).getJml_infant());
        int jmlbalita = Integer.parseInt(mFilterData.get(i).getJml_balita());
        int jmlbalitaKasur = Integer.parseInt(mFilterData.get(i).getJml_balita_kasur());
        int pax = jmldewasa + jmlinfant + jmlbalita + jmlbalitaKasur;
        viewHolder.alamat.setText(String.valueOf(pax)+ " PAX");

    }

    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {
                    mFilterData = dataProspeks;
                } else {

                    ArrayList<DataProspek> filterData = new ArrayList<>();

                    for (DataProspek data: dataProspeks) {

                        int jmldewasa = Integer.parseInt(data.getJml_dewasa());
                        int jmlinfant = Integer.parseInt(data.getJml_infant());
                        int jmlbalita = Integer.parseInt(data.getJml_balita());
                        int jmlbalitaKasur = Integer.parseInt(data.getJml_balita_kasur());
                        int pax = jmldewasa + jmlinfant + jmlbalita + jmlbalitaKasur;
                        String strPax = pax + " PAX";

                        if (data.getPic().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString) || strPax.toLowerCase().contains(charString)) {
                            filterData.add(data);
                        }
                    }

                    mFilterData = filterData;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterData = (ArrayList<DataProspek>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id,nama,telp,alamat;
        private LinearLayout detail, edit;
        String pic;
        String no_telp;
        String jml_dewasa;
        String jml_infant;
        String jml_balita;
        String jml_visa;
        String jml_balita_kasur;
        String tgl_keberangkatan;
        String jenis;
        String dobel;
        String triple;
        String quard;
        String passport;
        String meningitis;
        String pas_foto;
        String buku_nikah;
        String fc_akta;
        String visa_progresif;
        String diskon;
        String keterangan;
        String tanggal_followup;
        String perlengkapan_balita;
        String perlengkapan_dewasa;
        public ViewHolder(final View view) {
            super(view);

            id = (TextView)view.findViewById(R.id.idProspek);
            nama = (TextView)view.findViewById(R.id.namaProspek);
            telp = (TextView)view.findViewById(R.id.noTelp);
            alamat = (TextView)view.findViewById(R.id.alamatProspek);

            detail = (LinearLayout)view.findViewById(R.id.view);
            edit = (LinearLayout)view.findViewById(R.id.edit);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String smsNumber="919426640584@s.whatsapp.net";
                    Uri uri = Uri.parse("smsto:" + no_telp);
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.putExtra("sms_body", "aiwa");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.whatsapp");
                    context.startActivity(i);
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder((Activity) v.getContext());
                    adb.setTitle("Pembayaran DP/Lunas");
                    adb.setMessage("Segera hubungi Koordinator anda untuk konfirmasi!");
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String sessionId = id.getText().toString();
                            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            String id_agen = prefs.getString("iduser", "0");
                            HashMap<String, String> params = new HashMap<>();
                            //anggota_id
                            params.put("anggota_id", String.valueOf(id_agen));
                            params.put("pic", pic);
                            params.put("no_telp", no_telp);
                            params.put("jml_dewasa", jml_dewasa);
                            params.put("jml_infant", jml_infant);
                            params.put("jml_balita", jml_balita);
                            params.put("jml_visa", jml_visa);
                            params.put("jml_balita_kasur", jml_balita_kasur);
                            params.put("tgl_keberangkatan", tgl_keberangkatan);
                            params.put("jenis", jenis);
                            params.put("dobel", dobel);
                            params.put("triple", triple);
                            params.put("quard", quard);
                            params.put("passport", passport);
                            params.put("meningitis", meningitis);
                            params.put("pas_foto", pas_foto);
                            params.put("buku_nikah", buku_nikah);
                            params.put("fc_akta", fc_akta);
                            params.put("visa_progresif", visa_progresif);
                            params.put("diskon", diskon);
                            if (keterangan == null){
                                params.put("keterangan", "");
                            }else {
                                params.put("keterangan", keterangan);
                            }
                            params.put("tanggal_followup", tanggal_followup);
                            params.put("pembayaran", "SUDAH");
                            params.put("perlengkapan_balita", perlengkapan_balita);
                            params.put("perlengkapan_dewasa", perlengkapan_dewasa);

                            pDialog = new ProgressDialog((Activity) v.getContext());
                            pDialog.setMessage("Harap tunggu...");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            Call<ResponseBody> result = WebApi.getAPIService().bayarProspek(sessionId, params);
                            result.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    pDialog.dismiss();
                                    try {
                                        Log.d("RES", "onResponse: " + response.body());
                                        Toast.makeText((Activity) v.getContext(), "Pembayaran berhasil", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, KalkulasiActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(intent);

//                                        AlertDialog.Builder wa = new AlertDialog.Builder((Activity) v.getContext());
//                                        wa.setTitle("Apakah anda ");
//                                        wa.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                Intent shareIntent = new Intent();
//                                                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//                                                shareIntent.setType("text/plain");
//                                                shareIntent.putExtra(Intent.EXTRA_TEXT,  "Terimakasih " +pic);
//                                                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                context.startActivity(Intent.createChooser(shareIntent,"Share with"));
//                                            }
//                                        });
//                                        wa.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                Toast.makeText((Activity) v.getContext(), "Pembayaran berhasil", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(context, KalkulasiActivity.class);
//                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                context.startActivity(intent);
//                                            }
//                                        });
//                                        wa.show();

//                                        Uri uri = Uri.parse("smsto:" + no_telp);
//                                        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
//                                        i.putExtra(Intent.EXTRA_TEXT,"Terimakasih atas pembayarannya");
//                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        i.setPackage("com.whatsapp");
//                                        context.startActivity(i);
//
//                                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + no_telp +  "/*" + "?body=" + ""));
//                                        sendIntent.setPackage("com.whatsapp");
//                                        context.startActivity(sendIntent);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        Log.d("EXC", "onResponse: " + e.getMessage());
                                        Toast.makeText((Activity) v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    pDialog.dismiss();
                                    t.printStackTrace();
                                    Log.d("THROM", "onResponse: " + t.getMessage());
                                    Toast.makeText((Activity) v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    adb.show();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditKalkulasiActivity.class);
                    String sessionId = id.getText().toString();
                    intent.putExtra("id", sessionId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }

}
