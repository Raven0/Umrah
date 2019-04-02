package com.birutekno.aiwa.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.DataJadwal;
import com.birutekno.aiwa.model.Jadwal;
import com.birutekno.aiwa.model.Paket;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class JadwalAiwaAdapter extends RecyclerView.Adapter<JadwalAiwaAdapter.ViewHolder> implements Filterable {

    private final Context context;
    private ArrayList<DataJadwal> data;
    private ArrayList<DataJadwal> mFilterData;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    private PaketAiwaAdapter adapter;

    String berangkatDetail;
    String pulangDetail;
    String tglBerangkat;
    String tglPulang;
    String maskapai;
    String paketHari;
    String linkItinerary;

    public JadwalAiwaAdapter(ArrayList<DataJadwal> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public JadwalAiwaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_jadwalb, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalAiwaAdapter.ViewHolder viewHolder, int i) {
        //initialize Array List
        jadwal = new ArrayList<>(Arrays.asList(mFilterData.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        //initialize String to create a sentence from combined attribute
        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(Pukul " + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(Pukul" + jadwal.get(0).getJam_pulang() + ")" ;

        //assign attribute value into textView in recyclerview
        viewHolder.berangkat.setText(convertDate(jadwal.get(0).getTgl_berangkat()));
        viewHolder.pulang.setText(convertDate(jadwal.get(0).getTgl_pulang()));
        viewHolder.detailBerangkat.setText(jadwal.get(0).getRute_berangkat());
        viewHolder.detailPulang.setText(jadwal.get(0).getRute_pulang());
        viewHolder.paketHari.setText(jadwal.get(0).getJml_hari() + "\nSisa Seat : " + jadwal.get(0).getSisa());

        if(jadwal.get(0).getPromo() == 0){
            viewHolder.promot.setVisibility(View.GONE);
        }else if(jadwal.get(0).getPromo() == 1){
            viewHolder.promot.setVisibility(View.VISIBLE);
        }else {
            viewHolder.promot.setVisibility(View.GONE);
        }

        if (jadwal.get(0).getStatus().equals("SOLD OUT")){
            viewHolder.paketHari.setText("SOLD OUT");
        }

        adapter = new PaketAiwaAdapter(jadwal, paket, context);
        if (adapter.getItemCount() != 0){
            viewHolder.paketList.setAdapter(adapter);
            viewHolder.emptyData.setVisibility(View.GONE);
        } else {
            viewHolder.emptyData.setVisibility(View.VISIBLE);
        }

        tglBerangkat = jadwal.get(0).getTgl_berangkat();
        tglPulang = jadwal.get(0).getTgl_pulang();
        maskapai = jadwal.get(0).getMaskapai();
        paketHari = jadwal.get(0).getJml_hari() + " Hari";
        linkItinerary = jadwal.get(0).getItinerary();

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

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilterData = data;
                } else {

                    ArrayList<DataJadwal> filterData = new ArrayList<>();

                    for (DataJadwal data: data) {
                        jadwal = new ArrayList<>(Arrays.asList(data.getJadwal()));

                        if (convertDate(jadwal.get(0).getTgl_berangkat()).toLowerCase().contains(charString) || jadwal.get(0).getRute_berangkat().toLowerCase().contains(charString) || (jadwal.get(0).getJml_hari() + " Hari.").toLowerCase().contains(charString)) {
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
                mFilterData = (ArrayList<DataJadwal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang,paketHari,emptyData, promot;
        private RecyclerView paketList;
        private LinearLayout jadwal;
        private ExpandableLayout expandableLayout;
        public ViewHolder(View view) {
            super(view);

            jadwal = (LinearLayout)view.findViewById(R.id.jadwal);
            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            paketHari = (TextView)view.findViewById(R.id.paket);
            emptyData = (TextView)view.findViewById(R.id.emptyData);
            promot = (TextView)view.findViewById(R.id.promot);
            paketList = (RecyclerView)view.findViewById(R.id.listPaket);
            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);

            //initialize view for paket list recyclerview
            paketList.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            paketList.setLayoutManager(layoutManager);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.isExpanded()) {
                        expandableLayout.collapse();
                    } else {
                        expandableLayout.expand();
                    }
                }
            });

        }
    }

    public String convertDate(String args) {
        String date = args;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM yyyy");
        String newDateString = spf.format(newDate);

        return newDateString ;
    }

}
