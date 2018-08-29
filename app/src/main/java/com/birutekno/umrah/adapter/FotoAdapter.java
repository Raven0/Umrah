package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                try {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(i, "Share Image"));
                }catch (Exception ex){
                    Log.d("AHA", "onBitmapLoaded: " + ex.getMessage());
                    Toast.makeText(context, "Mendownload file gambar gagal!, silahkan coba lagi!", Toast.LENGTH_SHORT).show();
                }

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
                    Toast.makeText(context, "Memuat data...", Toast.LENGTH_SHORT).show();
                    //WEB
                    String url = link;
                    try {
                        shareImage(url, context);
                    }catch (Exception ex){
                        Log.d("SHARE_IMG", "onClick: " + ex.getMessage());
                        Toast.makeText(context, "Gambar gagal dimuat, silhakan coba lagi...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Developer msg" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

}
