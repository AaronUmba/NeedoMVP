package com.infined.needomvp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GoalActivity extends AppCompatActivity {
    public static final String TAG = "GoalActivity";
    private TextView title;
    private EditText goalInput;
    GoalDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.goalBar);
        title = (TextView) findViewById(R.id.titleG);
        goalInput = (EditText) findViewById(R.id.goalEnter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mDatabaseHelper = new GoalDatabaseHelper(this);


        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");
        final Typeface regface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Regular.ttf");
        final Typeface smallface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Light.ttf");

        goalInput.setText("");

        title.setTypeface(regface);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.goal_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done_goal) {

            goalInput = (EditText)findViewById(R.id.goalEnter);

            String Name = goalInput.getText().toString();

            int Pri = 100;

            if (goalInput.length() != 0) {
                AddData(Name, Pri);
                toastMessage("Task Name is" + Name);
                Intent i = new Intent(GoalActivity.this, ListDataActivity.class);
                Log.d(TAG,"STAGE 2 addData: Adding " + Name);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                finish();
            } else {
                toastMessage("You must put something in the text field!");
            }
        }

        if (id == R.id.action_back_goal) {
            Toast.makeText(GoalActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            Intent i = new Intent(GoalActivity.this, ListDataActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void AddData(String name, int pri) {
        boolean insertData = mDatabaseHelper.addData(name, pri);
        Log.d(TAG,"STAGE 1 addData: Adding " + name);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
