package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.birutekno.umrah.helper.Shortcuts;
import com.birutekno.umrah.ui.BaseActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;

/**
 * Created by No Name on 8/7/2017.
 */

public class MapActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.gif)
    SimpleDraweeView gif;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Shortcuts.parseDrawableToUri(String.valueOf(R.drawable.animasi)))
                .setAutoPlayAnimations(true)
                .build();
        gif.setController(controller);
    }
}
