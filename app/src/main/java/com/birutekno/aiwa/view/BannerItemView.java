package com.birutekno.aiwa.view;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.helper.Shortcuts;
import com.birutekno.aiwa.model.Banner;
import com.birutekno.aiwa.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.aiwa.ui.adapter.viewholder.BaseItemViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;

/**
 * Created by No Name on 7/29/2017.
 */

public class BannerItemView extends BaseItemViewHolder<Banner> {

    @Bind(R.id.image)
    protected SimpleDraweeView mImage;

    protected OnActionListener mActionListener;
    protected Banner banner;

    public void setOnActionListener(OnActionListener listener) {
        mActionListener = listener;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (mActionListener != null) {
            mActionListener.onClick(this);
        }
    }

    public BannerItemView(Context context, View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        this.mContext = context;
    }

    @Override
    public void bind(Banner banner) {
        setBanner(banner);
        mImage.setImageURI(Shortcuts.parseDrawableToUri(String.valueOf(banner.getImage())));
    }

    public interface OnActionListener {
        void onClick(BannerItemView view);
    }
}
