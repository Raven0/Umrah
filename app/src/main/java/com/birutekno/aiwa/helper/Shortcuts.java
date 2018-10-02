package com.birutekno.aiwa.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.birutekno.aiwa.R;
import com.facebook.common.util.UriUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Suitmedia on 4/20/2016.
 */
public class Shortcuts {

    private static ProgressDialog dialog;

    public static MaterialDialog showAlertDialog(Context context, String title, String message) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.theme(Theme.LIGHT);
        if (title != null) builder.title(title);
        if (message != null) builder.content(message);
        return builder
                .positiveText("OK")
                .show();
    }

    public static Date parseISO8601Date(String dateString) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            date = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFullDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String dt = sdf.format(date);

        String split[] = dt.split(" ");

        return split[0] + " " + split[1] + " " + split[2];
    }

    public static String getShortDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        String dt = sdf.format(date);

        String split[] = dt.split(" ");

        return split[0] + "/" + split[1] + "/" + split[2];
    }

    public static String getTime(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dt = sdf.format(date);

        return dt;
    }

    public static String getFullDateTime(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH mm");
        String dt = sdf.format(date);

        String split[] = dt.split(" ");

        return split[0] + " " + split[1] + " " + split[2] + " " + split[3] + "." + split[4];
    }

    public static String getIndonesianMonth(String month) {
        String mm = "";
        if (StringUtils.equalsIgnoreCase(month, "January")) {
            mm = "Januari";
        } else if (StringUtils.equalsIgnoreCase(month, "February")) {
            mm = "Februari";
        } else if (StringUtils.equalsIgnoreCase(month, "March")) {
            mm = "Maret";
        } else if (StringUtils.equalsIgnoreCase(month, "April")) {
            mm = "April";
        } else if (StringUtils.equalsIgnoreCase(month, "May")) {
            mm = "Mei";
        } else if (StringUtils.equalsIgnoreCase(month, "June")) {
            mm = "Juni";
        } else if (StringUtils.equalsIgnoreCase(month, "July")) {
            mm = "Juli";
        } else if (StringUtils.equalsIgnoreCase(month, "August")) {
            mm = "Agustus";
        } else if (StringUtils.equalsIgnoreCase(month, "September")) {
            mm = "September";
        } else if (StringUtils.equalsIgnoreCase(month, "October")) {
            mm = "Oktober";
        } else if (StringUtils.equalsIgnoreCase(month, "November")) {
            mm = "November";
        } else if (StringUtils.equalsIgnoreCase(month, "December")) {
            mm = "Desember";
        }
        return mm;
    }

    public static void shareVia(Activity activity, String type, String desc, String id) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Bali United");
        if (type.equals("news")) {
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://rkpw9.app.goo.gl/?link=http://www.baliutd.com/" + type + "/" + desc + "/" + id + "&apn=com.baliutd.android&afl=http://www.baliutd.com/" + type + "/" + desc + "&ifl=http://www.baliutd.com/" + type + "/" + desc);
        } else if (type.equals("video")) {
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://rkpw9.app.goo.gl/?link=https://www.youtube.com/watch?v=" + desc + "/" + id + "&apn=com.baliutd.android&afl=https://www.youtube.com/watch?v=" + desc + "&ifl=https://www.youtube.com/watch?v=" + desc);
        }
        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void shareVia(Activity activity, String link) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Bali United");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, link);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static String randString(ArrayList<String> array) {
        String word = array.get((int) (Math.random() * array.size()));
        return word;
    }

    public static String convertSecondToMinute(String second) {
        int sec = Integer.valueOf(second);
        int minute = sec / 60;
        return String.valueOf(minute) + "'";
    }

    public static void getKeyHash(Context context) {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.baliutd.android",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String checkNullString(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    public static String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static Uri parseDrawableToUri(String imagePath) {
        return new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(imagePath)
                .build();
    }

    public static void gotoPlaystore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String getFullMonth(String month){
        String mm = "";
        if (StringUtils.equalsIgnoreCase(month, "01")){
            mm = "Januari";
        }else if (StringUtils.equalsIgnoreCase(month, "02")){
            mm = "Februari";
        }else if (StringUtils.equalsIgnoreCase(month, "03")){
            mm = "Maret";
        }else if (StringUtils.equalsIgnoreCase(month, "04")){
            mm = "April";
        }else if (StringUtils.equalsIgnoreCase(month, "05")){
            mm = "Mei";
        }else if (StringUtils.equalsIgnoreCase(month, "06")){
            mm = "Juni";
        }else if (StringUtils.equalsIgnoreCase(month, "07")){
            mm = "Juli";
        }else if (StringUtils.equalsIgnoreCase(month, "08")){
            mm = "Agustus";
        }else if (StringUtils.equalsIgnoreCase(month, "09")){
            mm = "September";
        }else if (StringUtils.equalsIgnoreCase(month, "10")){
            mm = "Oktober";
        }else if (StringUtils.equalsIgnoreCase(month, "11")){
            mm = "November";
        }else if (StringUtils.equalsIgnoreCase(month, "12")){
            mm = "Desember";
        }
        return mm;
    }
}
