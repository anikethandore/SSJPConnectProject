package com.anikethandore.ssjpconnect.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;

import com.anikethandore.ssjpconnect.R;

public class SplashscreenActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN=2000; //this means 3 sec splash screen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (isConnected(this)) {
        //go to new after splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //going to login screen
                Intent intent = new Intent(SplashscreenActivity.this, HomeActivity.class);
                SplashscreenActivity.this.startActivity(intent);
                SplashscreenActivity.this.finish();
            }
        },SPLASH_SCREEN);
        }
        else {
            showDialogBox();
        }
    } private boolean isConnected(Context context) {

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || dataConn !=null && dataConn.isConnected()){
            return true;
        }
        else {
            return false;
        }
    }


    private void showDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Check your Internet Connection!")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SplashscreenActivity.super.onBackPressed();
                    }
                }).show();
    }
}
