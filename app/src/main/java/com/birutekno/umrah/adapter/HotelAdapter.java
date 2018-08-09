package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.birutekno.umrah.DetailHotelActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataHotel;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder>{

    private ArrayList<DataHotel> data;
    private ArrayList<DataHotel> mFilterData;
    Context context;

    public HotelAdapter(ArrayList<DataHotel> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public HotelAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_hotel, viewGroup, false);
        return new HotelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelAdapter.ViewHolder viewHolder, int i) {
        viewHolder.idhotel = mFilterData.get(i).getId();
        viewHolder.namaHotel = mFilterData.get(i).getNama_hotel();
        viewHolder.judul.setText(mFilterData.get(i).getNama_hotel());
        viewHolder.rating.setText(mFilterData.get(i).getSkor());

        int skors = Integer.parseInt(mFilterData.get(i).getSkor());
        if (skors == 1){
            viewHolder.star1.setImageResource(R.drawable.icon_bintang);
            viewHolder.star2.setImageResource(R.drawable.bintang2);
            viewHolder.star3.setImageResource(R.drawable.bintang2);
            viewHolder.star4.setImageResource(R.drawable.bintang2);
            viewHolder.star5.setImageResource(R.drawable.bintang2);
        }else if (skors == 2){
            viewHolder.star1.setImageResource(R.drawable.icon_bintang);
            viewHolder.star2.setImageResource(R.drawable.icon_bintang);
            viewHolder.star3.setImageResource(R.drawable.bintang2);
            viewHolder.star4.setImageResource(R.drawable.bintang2);
            viewHolder.star5.setImageResource(R.drawable.bintang2);
        }else if (skors == 3){
            viewHolder.star1.setImageResource(R.drawable.icon_bintang);
            viewHolder.star2.setImageResource(R.drawable.icon_bintang);
            viewHolder.star3.setImageResource(R.drawable.icon_bintang);
            viewHolder.star4.setImageResource(R.drawable.bintang2);
            viewHolder.star5.setImageResource(R.drawable.bintang2);
        }else if (skors == 4){
            viewHolder.star1.setImageResource(R.drawable.icon_bintang);
            viewHolder.star2.setImageResource(R.drawable.icon_bintang);
            viewHolder.star3.setImageResource(R.drawable.icon_bintang);
            viewHolder.star4.setImageResource(R.drawable.icon_bintang);
            viewHolder.star5.setImageResource(R.drawable.bintang2);
        }else if (skors == 5){
            viewHolder.star1.setImageResource(R.drawable.icon_bintang);
            viewHolder.star2.setImageResource(R.drawable.icon_bintang);
            viewHolder.star3.setImageResource(R.drawable.icon_bintang);
            viewHolder.star4.setImageResource(R.drawable.icon_bintang);
            viewHolder.star5.setImageResource(R.drawable.icon_bintang);
        }else{
//            star1.setBackgroundResource(R.drawable.bintang2);
//            star2.setBackgroundResource(R.drawable.bintang2);
//            star3.setBackgroundResource(R.drawable.bintang2);
//            star4.setBackgroundResource(R.drawable.bintang2);
//            star5.setBackgroundResource(R.drawable.bintang2);
        }
//        viewHolder.link = "http://"+mFilterData.get(i);
//        try {
//            Picasso.get().load(viewHolder.link).fit().centerCrop().into(viewHolder.imageView);
//        }catch (Exception ex){
//            Log.d("ERROR_MSG", "onBindViewHolder: " + ex.getMessage());
//        }
    }

    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView judul, rating;
        private ImageView star1,star2,star3,star4,star5;
//        String link;
        String idhotel;
        String namaHotel;
        public ViewHolder(final View view) {
            super(view);

            judul = (TextView)view.findViewById(R.id.judul);
            rating = (TextView)view.findViewById(R.id.rating);
            star1 = (ImageView)view.findViewById(R.id.star1);
            star2 = (ImageView)view.findViewById(R.id.star2);
            star3 = (ImageView)view.findViewById(R.id.star3);
            star4 = (ImageView)view.findViewById(R.id.star4);
            star5 = (ImageView)view.findViewById(R.id.star5);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailHotelActivity.class);
                    intent.putExtra("id", idhotel);
                    intent.putExtra("nama", namaHotel);
                    context.startActivity(intent);
                }
            });

        }
    }

}
