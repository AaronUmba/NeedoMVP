package com.infined.needomvp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener ,BottomSheetFragment.BottomSheetListener{

    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;
    GoalDatabaseHelper mDatabaseHelperGoal;
    private EditText editText;
    private EditText editDesc;
    private Button btnAdd ;
    private ImageButton btnExit;
    private TextView title, sVal, pVal, uVal;
    private SeekBar strategic, personal, urgent;
    private int newTaskPriority = 100;
    private TextView goalName;
    private ListView mListView;
    String newEntry;
    String newDescriptionEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editDesc = (EditText) findViewById(R.id.editDesc);
        btnAdd = (Button) findViewById(R.id.addTaskFab2);
        btnExit = (ImageButton) findViewById(R.id.btnExit1);
        title = (TextView) findViewById(R.id.addTitle);
        android.support.v7.widget.Toolbar titleLayout = (android.support.v7.widget.Toolbar) findViewById(R.id.layoutTitleEdit);
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelperGoal = new GoalDatabaseHelper(this);
        strategic = (SeekBar)findViewById(R.id.strategic);
        personal = (SeekBar)findViewById(R.id.personal);
        urgent = (SeekBar)findViewById(R.id.urgency);
        sVal = (TextView)findViewById(R.id.stratVal);
        pVal = (TextView)findViewById(R.id.personalVal);
        uVal = (TextView)findViewById(R.id.urgencyVal);
        editDesc.setText("");
        editText.setText("");
        mListView = findViewById(R.id.goalListS);



        setSupportActionBar(titleLayout);

        getSupportActionBar().setTitle("");

        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");
        Typeface regface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Regular.ttf");
        Typeface smallface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Light.ttf");

        title.setTypeface(regface);

        int bar1 = strategic.getProgress();
        int bar2 = personal.getProgress();
        int bar3 = urgent.getProgress();

        editText.setTypeface(face);
        btnAdd.setTypeface(regface);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strategic.setProgress(5);
                personal.setProgress(5);
                urgent.setProgress(5);

                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEntry = editText.getText().toString();
                newDescriptionEntry = editDesc.getText().toString();

                int bar1 = strategic.getProgress();
                int bar2 = personal.getProgress();
                int bar3 = urgent.getProgress();

                Intent receivedIntent = getIntent();

                int goalID = receivedIntent.getIntExtra("ID", -1);

                int priorityGoal = receivedIntent.getIntExtra("PRI", 0);

                int barTotal = bar1 + bar2 + bar3;

                int taskPriority = newTaskPriority + barTotal + priorityGoal;

                if (editText.length() != 0) {
                    AddData(newEntry, newDescriptionEntry, taskPriority, bar1, bar2, bar3, goalID);
                    Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                    finish();
                } else {
                    toastMessage("You must put something in the text field!");
                }

                strategic.setProgress(5);
                personal.setProgress(5);
                urgent.setProgress(5);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public void onButtonClicked(String text) {

    }

    public void AddData(String newEntry, String newDescriptionEntry, int newTaskPriority, int strat, int person, int urgen, int goal) {
        boolean insertData = mDatabaseHelper.addData(newEntry, newDescriptionEntry, newTaskPriority, strat, person, urgen, goal);

        if(!insertData){
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int val = (i * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        TextView ind1 = findViewById(R.id.indicator1);
        TextView ind2 = findViewById(R.id.indicator2);
        TextView ind3 = findViewById(R.id.indicator3);
        ind1.setText("" + i);
        ind1.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}


