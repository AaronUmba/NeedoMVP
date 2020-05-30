package com.infined.needomvp;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity{

    private Button btnSave;
    private ImageButton btnDelete;
    private ImageButton btnExit;
    private EditText editable_item;
    private EditText editable_desc;
    private TextView title;
    private int strat, person, urgen;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private String selectedDesc;
    private int selectedID;
    private int selectedPriority;

    private SeekBar strategic, personal, urgent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        btnSave = findViewById(R.id.editTaskFab);
        btnDelete = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.btnExit);
        editable_item = findViewById(R.id.editable_item);
        editable_desc = findViewById(R.id.editable_desc);
        title = findViewById(R.id.editTitle);
        mDatabaseHelper = new DatabaseHelper(this);
        strategic = findViewById(R.id.strategicE);
        personal = findViewById(R.id.personalE);
        urgent = findViewById(R.id.urgencyE);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");

        title.setTypeface(face);
        editable_item.setTypeface(face);
        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        selectedPriority = receivedIntent.getIntExtra("priority",100);

        strat = receivedIntent.getIntExtra("strat", 5);

        person = receivedIntent.getIntExtra("person", 5);

        urgen = receivedIntent.getIntExtra("urgen", 5);

        selectedName = receivedIntent.getStringExtra("name");

        selectedDesc = receivedIntent.getStringExtra("description");

        DatabaseUtils.sqlEscapeString(selectedName);
        DatabaseUtils.sqlEscapeString(selectedDesc);

        strategic.setProgress(strat);
        personal.setProgress(person);
        urgent.setProgress(urgen);

        editable_item.setText(selectedName);
        editable_desc.setText(selectedDesc);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                String desc = editable_desc.getText().toString();

                int bar1 = strategic.getProgress();
                int bar2 = personal.getProgress();
                int bar3 = urgent.getProgress();

                int barTotal = bar1 + bar2 + bar3;

                int taskPriority = 100 + barTotal;

                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                    mDatabaseHelper.updateDesc(desc,selectedID,selectedDesc);
                    mDatabaseHelper.updatePriority(taskPriority,selectedID,selectedPriority);
                    mDatabaseHelper.updateBar1(bar1,selectedID,strat);
                    mDatabaseHelper.updateBar2(bar2,selectedID,person);
                    mDatabaseHelper.updateBar3(bar3,selectedID,urgen);
                    startActivity(new Intent(EditDataActivity.this, ListDataActivity.class));
                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                    finish();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                String desc = editable_desc.getText().toString();

                int bar1 = strategic.getProgress();
                int bar2 = personal.getProgress();
                int bar3 = urgent.getProgress();

                int barTotal = bar1 + bar2 + bar3;

                int taskPriority = 100 + barTotal;

                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                    mDatabaseHelper.updateDesc(desc,selectedID,selectedDesc);
                    mDatabaseHelper.updatePriority(taskPriority,selectedID,selectedPriority);
                    mDatabaseHelper.updateBar1(bar1,selectedID,strat);
                    mDatabaseHelper.updateBar2(bar2,selectedID,person);
                    mDatabaseHelper.updateBar3(bar3,selectedID,urgen);
                    startActivity(new Intent(EditDataActivity.this, ListDataActivity.class));
                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                    finish();
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName);
                startActivity(new Intent(EditDataActivity.this,ListDataActivity.class));
                overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                finish();
            }
        });


        }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

}
