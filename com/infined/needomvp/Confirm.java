package com.infined.needomvp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Confirm extends AppCompatActivity {

    private static int CONFIRM_TIMER = 2000;
    private static int DELAY = 250;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        title = (TextView)findViewById(R.id.conf);
        final RelativeLayout back = (RelativeLayout)findViewById(R.id.backC);

        final Animation animation = AnimationUtils.loadAnimation(Confirm.this, R.anim.slide_in);
        Animation animationSmall = AnimationUtils.loadAnimation(Confirm.this, R.anim.slide_in_small);
        Animation animationSmallDel = AnimationUtils.loadAnimation(Confirm.this, R.anim.slide_in_small_delayed_1);
        final Animation animationDownSmall = AnimationUtils.loadAnimation(Confirm.this, R.anim.slide_down_small);

        title.startAnimation(animationSmall);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");
        Typeface regface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Regular.ttf");
        Typeface smallface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Light.ttf");

        title.setTypeface(smallface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                title.startAnimation(animationDownSmall);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        back.setVisibility(View.VISIBLE);
                        Intent i = new Intent(Confirm.this, ListDataActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                        finish();
                    }
                }, DELAY);
            }
        }, CONFIRM_TIMER);
    }
}
