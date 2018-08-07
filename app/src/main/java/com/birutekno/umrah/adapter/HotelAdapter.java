package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.birutekno.umrah.DetailHotelActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataHotel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
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

    static public void shareImage(String url, final Context context) {
        Picasso.get().load(url).into(new com.squareup.picasso.Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView judul, rating;
//        private ImageView imageView;
//        String link;
        String idhotel;
        String namaHotel;
        public ViewHolder(final View view) {
            super(view);

            judul = (TextView)view.findViewById(R.id.judul);
            rating = (TextView)view.findViewById(R.id.rating);

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
