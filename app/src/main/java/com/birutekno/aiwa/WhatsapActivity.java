package com.birutekno.aiwa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class WhatsapActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, WhatsapActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsap);

        ImageView img = (ImageView) findViewById(R.id.image);
        img.setImageResource(R.drawable.brosur1);
//        final Uri path = null;
//        img.setImageURI(path);

        final EditText message = (EditText) findViewById(R.id.editText1);
        Button btn  = (Button) findViewById(R.id.message);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatsAppMessage = message.getText().toString();

                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.brosur1);
                String path = getExternalCacheDir()+"/shareimage.jpg";
                java.io.OutputStream out = null;
                java.io.File file=new java.io.File(path);
                try { out = new java.io.FileOutputStream(file); bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); out.flush(); out.close(); } catch (Exception e) { e.printStackTrace(); } path=file.getPath();
                Uri bmpUri = Uri.parse("file://"+path);

                Intent shareIntent = new Intent();
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("*/*");

                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
//                shareIntent.setType("image/jpg");
                startActivity(Intent.createChooser(shareIntent,"Share with"));

            }
        });

    }
}
