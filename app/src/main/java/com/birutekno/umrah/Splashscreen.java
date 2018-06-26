package com.birutekno.umrah;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class Splashscreen extends AppCompatActivity {
    private static int splashInterval = 2000;
    private int intro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if(intro == 1){
                    Intent i = new Intent(Splashscreen.this, LoginActivity.class);
                    startActivity(i);
                    this.finish();
                    intro = 1;

                }else {
                    Intent i = new Intent(Splashscreen.this, WizardActivity.class);
                    startActivity(i);
                    this.finish();
                    intro = 1;

                }

            }

            private void finish() {
                intro = 1;
            }
        }, splashInterval);

    };
}
