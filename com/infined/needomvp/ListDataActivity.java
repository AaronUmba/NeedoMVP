package com.infined.needomvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListDataActivity extends AppCompatActivity implements BottomSheetFragment.BottomSheetListener {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;
    private TextView mTaskName;
    private TextView mTaskDesc;
    private TextView menuName;
    private TextView title;
    private RelativeLayout changeName;
    private ListView mListView;
    private FrameLayout bottomSheet;
    private FloatingActionButton addFab;
    private TextView empty;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        mTaskDesc = (TextView) findViewById(R.id.task_desc);
        mTaskName = (TextView) findViewById(R.id.task_name);
        title = (TextView) findViewById(R.id.listTitle);
        addFab = (FloatingActionButton)findViewById(R.id.addTaskFab);
        empty = (TextView)findViewById(R.id.emptyView);
        changeName = (RelativeLayout)findViewById(R.id.changeName);
        Animation animation = AnimationUtils.loadAnimation(ListDataActivity.this, R.anim.slide_in);
        Animation animationSmall = AnimationUtils.loadAnimation(ListDataActivity.this, R.anim.slide_in_small);

        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");
        final Typeface regface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Regular.ttf");
        final Typeface smallface = Typeface.createFromAsset(getAssets(),
                "fonts/SourceSansPro-Light.ttf");

        title.setTypeface(regface);

        title.setAnimation(animationSmall);
        empty.setAnimation(animationSmall);

        String username = null;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username", username);

        if (username != null){
            title.setText("Welcome " + username + ". Here's what needs doing.");
            if (username.length() > 10){
                title.setText("Welcome " + username);
            }
        } else {
            title.setText("Welcome. Here's what needs doing.");
        }

        ImageButton menu = (ImageButton)findViewById(R.id.listMenu);
        ImageButton more = (ImageButton)findViewById(R.id.listMore);
        bottomSheet = (FrameLayout)findViewById(R.id.bottom_sheet_list);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment();
                bottomSheet.show(getSupportFragmentManager(), "bottomSheet");


            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment2 bottomSheet2 = new BottomSheetFragment2();
                bottomSheet2.show(getSupportFragmentManager(), "bottomSheet2");
            }
        });

        mListView.setEmptyView(findViewById(R.id.emptyView));

        populateListView();
    }
    private void populateListView() {
        addFab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(ListDataActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
        }
    });

        Animation animation = AnimationUtils.loadAnimation(ListDataActivity.this, R.anim.slide_in_small);
        final Animation fade_in = AnimationUtils.loadAnimation(ListDataActivity.this, R.anim.fade_in_q);

        mListView.setAnimation(animation);

        String[] fromColumns = mDatabaseHelper.databaseToStringArray();

        int[] toViews = new int[]{R.id.task_name, R.id.task_desc};

        Cursor cursor;

        cursor = mDatabaseHelper.getWritableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " ORDER BY " + DatabaseHelper.COL3 + " DESC", null);

        SimpleCursorAdapter customAdapter = new CustomAdapter(this, R.layout.list_item, cursor, fromColumns, toViews,0);

        mListView.setAdapter(customAdapter);

        int length = mListView.getAdapter().getCount();

        if (length == 0){
            empty.startAnimation(fade_in);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView taskName = view.findViewById(R.id.task_name); String name = taskName.getText().toString();
                TextView taskDesc = view.findViewById(R.id.task_desc); String desc = taskDesc.getText().toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mDatabaseHelper.getItemID(name);//get the id associated with that name

                int priority = 100;
                int seekbar1 = 5;
                int seekbar2 = 5;
                int seekbar3 = 5;
                int itemID = -1;
                while(data.moveToNext()){
                     itemID = data.getInt(0);
                }
                Cursor pri = mDatabaseHelper.getItemPriority(itemID);
                while(pri.moveToNext()){
                    priority = pri.getInt(0);
                }
                Cursor bar1 = mDatabaseHelper.getBar1(itemID);
                while(bar1.moveToNext()){
                    seekbar1 = bar1.getInt(0);
                }
                Cursor bar2 = mDatabaseHelper.getBar2(itemID);
                while(bar2.moveToNext()){
                    seekbar2 = bar2.getInt(0);
                }
                Cursor bar3 = mDatabaseHelper.getBar3(itemID);
                while(bar3.moveToNext()){
                    seekbar3 = bar3.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);

                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    editScreenIntent.putExtra("priority",priority);
                    editScreenIntent.putExtra("description",desc);
                    editScreenIntent.putExtra("strat",seekbar1);
                    editScreenIntent.putExtra("person", seekbar2);
                    editScreenIntent.putExtra("urgen", seekbar3);
                    startActivity(editScreenIntent);

                    overridePendingTransition(R.anim.fade_in_q, R.anim.fade_out_q);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }
    public void deleteTask(View view){
        final View parent = (View)view.getParent();
        final TextView taskName = parent.findViewById(R.id.task_name);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_q);
        CardView cv = findViewById(R.id.cv);
        cv.startAnimation(fadeOut);
        cv.setVisibility(View.GONE);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String task_name = String.valueOf(taskName.getText());
                Cursor data = mDatabaseHelper.getItemID(task_name);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                mDatabaseHelper.deleteName(itemID,task_name);
                populateListView();
            }
        }, 250);

    }
    public void deleteAll(View view){
        mDatabaseHelper.deleteAll();
        toastMessage("All Items Were Deleted");
        populateListView();
    }

    public void changeName(final View view){
        final EditText UsernameInput = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Change Name")
                .setMessage("Username")
                .setView(UsernameInput)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (UsernameInput != null){
                        String username = null;
                        String newUsername = UsernameInput.getText().toString();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ListDataActivity.this);
                        prefs.edit().putString("username", newUsername).apply();
                        username = prefs.getString("username", username);

                        if (username != null){
                            TextView menuN = view.findViewById(R.id.MenuName);
                            title.setText("Welcome " + username + ". Here's what needs doing.");
                        } else {
                            title.setText("Welcome. Here's what needs doing.");
                            }
                        } else {
                            toastMessage("You must put something in the field");
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onButtonClicked(String text) {

    }

}

