package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataGallery;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder>{

    private ArrayList<DataGallery> data;
    private ArrayList<DataGallery> mFilterData;
    Context context;

    public FotoAdapter(ArrayList<DataGallery> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public FotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_foto, viewGroup, false);
        return new FotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FotoAdapter.ViewHolder viewHolder, int i) {
        viewHolder.judul.setText(mFilterData.get(i).getJudul());
        viewHolder.link = "http://"+mFilterData.get(i).getFile();
        try {
            Picasso.get().load(viewHolder.link).fit().centerCrop().into(viewHolder.imageView);
        }catch (Exception ex){
            Log.d("ERROR_MSG", "onBindViewHolder: " + ex.getMessage());
        }
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
        private TextView judul;
        private ImageView imageView;
        String link;
        public ViewHolder(final View view) {
            super(view);

            judul = (TextView)view.findViewById(R.id.judul);
            imageView = (ImageView) view.findViewById(R.id.imgView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String shareBody = link;
//                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                    sharingIntent.setType("text/plain");
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                    context.startActivity(Intent.createChooser(sharingIntent, "Share"));

                    //WEB
                    String url = link;
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    context.startActivity(i);
                    shareImage(url, context);
                }
            });

        }
    }

}
