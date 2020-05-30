package com.infined.needomvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Name extends AppCompatActivity {

    private static int DELAY = 250;
    private TextView title;
    private EditText name;
    private Button confirm;
    private TextView preface;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        title = (TextView)findViewById(R.id.nameTitle);
        name = (EditText)findViewById(R.id.name);
        preface = (TextView) findViewById(R.id.preface);
        confirm = (Button)findViewById(R.id.nameButton);
        back = (RelativeLayout)findViewById(R.id.backN);

        Animation animation = AnimationUtils.loadAnimation(Name.this, R.anim.slide_in);
        Animation animationSmall = AnimationUtils.loadAnimation(Name.this, R.anim.slide_in_small);
        Animation animationSmallDel = AnimationUtils.loadAnimation(Name.this, R.anim.slide_in_small_delayed_1);
        final Animation animationDownSmall = AnimationUtils.loadAnimation(Name.this, R.anim.slide_down_small);
        final Animation animationDown = AnimationUtils.loadAnimation(Name.this, R.anim.slide_down);

        title.startAnimation(animationSmall);
        name.startAnimation(animationSmall);
        confirm.startAnimation(animationSmall);
        preface.startAnimation(animationSmall);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");
        Typeface regface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Regular.ttf");
        Typeface smallface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Light.ttf");

        title.setTypeface(smallface);
        name.setTypeface(face);
        confirm.setTypeface(smallface);

        name.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            enterData();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterData();
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    private void enterData(){
        final Animation animationDown = AnimationUtils.loadAnimation(Name.this, R.anim.slide_down);
        if (name.length() != 0) {
            title.startAnimation(animationDown);
            name.startAnimation(animationDown);
            confirm.startAnimation(animationDown);
            preface.startAnimation(animationDown);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String username = name.getText().toString();
                    back.setVisibility(View.VISIBLE);
                    Intent i = new Intent(Name.this, Confirm.class);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Name.this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", username); //InputString: from the EditText
                    editor.apply();
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                    finish();
                }
            }, DELAY);
        } else {
            toastMessage("Please Add a Name");

        }
    }

}
