package com.birutekno.aiwa.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.DataGallery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    private ArrayList<DataGallery> data;
    private ArrayList<DataGallery> mFilterData;
    Context context;

    public VideoAdapter(ArrayList<DataGallery> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_video, viewGroup, false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder viewHolder, int i) {
        viewHolder.judul.setText(mFilterData.get(i).getJudul());
        String link = "http://"+mFilterData.get(i).getFile();
        viewHolder.yt = mFilterData.get(i).getDeskripsi();
        try {
            Picasso.get().load(link).fit().centerCrop().into(viewHolder.imageView);
        }catch (Exception ex){
            Log.d("ERROR_MSG", "onBindViewHolder: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView judul;
        private ImageView imageView;
        String yt;
        public ViewHolder(final View view) {
            super(view);

            judul = (TextView)view.findViewById(R.id.judul);
            imageView = (ImageView) view.findViewById(R.id.imgView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Memuat data...", Toast.LENGTH_SHORT).show();

                    String url = yt;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    try {
                        context.startActivity(i);
                    }catch (Exception ex){
                        Toast.makeText(context, "Link youtube tidak valid! silahkan hubungi koordinator anda!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

}
