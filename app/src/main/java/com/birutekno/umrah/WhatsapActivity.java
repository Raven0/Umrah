package com.birutekno.umrah;

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

//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
//                sendIntent.setType("text/plain");
//
//                // Do not forget to add this to open whatsApp App specifically
//                sendIntent.setPackage("com.whatsapp");
//                startActivity(sendIntent);

//                YANG INI
//                Intent myIntent = new Intent(Intent.ACTION_SEND);
//                myIntent.setType("text/plain");
////                myIntent.setType("image/jpeg");
//                String shareBody = whatsAppMessage;
//                String shareSub = whatsAppMessage;
//                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
//                myIntent.putExtra(Intent.EXTRA_TEXT,shareSub);
//                myIntent.putExtra(Intent.EXTRA_STREAM, path);
//                startActivity(Intent.createChooser(myIntent, "Share Using"));

//                String text = "Look at my awesome picture";
//                Uri pictureUri = Uri.parse(String.valueOf(R.drawable.brosur1));
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
//                shareIntent.setType("image/*");
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                startActivity(Intent.createChooser(shareIntent, "Share images..."));

//                Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.brosur1);
//                Intent share1 = new Intent(Intent.ACTION_SEND);
//                share1.setType("image/jpeg");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
//                        b, "Title", null);
//                Uri imageUri1 =  Uri.parse(path);
//                share1.putExtra(Intent.EXTRA_STREAM, imageUri1);
//                startActivity(Intent.createChooser(share1, "Share Image using"));

//                Uri imageuri2=Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.brosur4);
//
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/jpeg");
//                share.putExtra(Intent.EXTRA_STREAM, imageuri2);
//                startActivity(Intent.createChooser(share, "Share Image using"));

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
