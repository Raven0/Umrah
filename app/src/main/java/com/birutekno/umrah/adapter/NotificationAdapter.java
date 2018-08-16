package com.birutekno.umrah.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataNotification;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aldio Firando on 8/15/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public static final String PREFS_NAME = "AUTH";

    private ArrayList<DataNotification> data;
    private ArrayList<DataNotification> mFilterData;
    private ProgressDialog pDialog;
    Context context;

    public NotificationAdapter(ArrayList<DataNotification> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_notification, viewGroup, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder viewHolder, int i) {

        viewHolder.pesan.setText(mFilterData.get(i).getPesan());
        viewHolder.id = Integer.parseInt(mFilterData.get(i).getId());

    }

    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private int id;
        private CardView cardView;
        private TextView pesan;
        private Button btn;
        public ViewHolder(final View view) {
            super(view);

            pesan = (TextView)view.findViewById(R.id.pesan);
            btn = (Button) view.findViewById(R.id.queryButton);
            cardView = (CardView)view.findViewById(R.id.cardNotif);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    HashMap<String, String> params = new HashMap<>();
                    //anggota_id
                    params.put("status", "READ");

                    Call<ResponseBody> result = WebApi.getAPIService().readNotif(String.valueOf(id), params);
                    result.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.body()!=null){
                                    cardView.animate()
                                            .translationX(view.getWidth())
                                            .alpha(0.0f)
                                            .setDuration(250)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    cardView.setVisibility(View.GONE);
                                                }
                                            });
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.d("EXC", "onResponse: " + e.getMessage());
                                Toast.makeText((Activity) v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            Log.d("THROM", "onResponse: " + t.getMessage());
                            Toast.makeText((Activity) v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }
}
