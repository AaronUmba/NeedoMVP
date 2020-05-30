package com.infined.needomvp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        final Intent firstIntent = new Intent(SplashScreen.this, Welcome.class);
        final Intent secondIntent = new Intent(SplashScreen.this, ListDataActivity.class);

        if (firstStart) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(firstIntent);
                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstStart", false);
                    editor.apply();
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else if (!firstStart) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(secondIntent);
                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}

