package com.infined.needomvp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.infined.needomvp.BottomSheetFragment.mListviewg;
import static com.infined.needomvp.GoalDatabaseHelper.COL4;

public class CustomGoalAdapter extends SimpleCursorAdapter {
    private int layout;
    private Context context;
    private GoalDatabaseHelper mDatabaseHelper;
    private CheckBox cbx;
    private boolean checked;
    private int primaryGoal = 10;
    private int unselectGoal = 0;


    public CustomGoalAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup parent) {
        Cursor c = getCursor();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item_goal, parent, false);
        bindView(v, context, c);

        mDatabaseHelper = new GoalDatabaseHelper(v.getContext());

        return v;
    }

    @Override
    public void bindView(View v, Context context, final Cursor c) {

        int goalIDColumn = c.getColumnIndex(GoalDatabaseHelper.COL1);
        final int goalNameColumn = c.getColumnIndex(GoalDatabaseHelper.COL2);
        int goalPriColumn = c.getColumnIndex(GoalDatabaseHelper.COL3);
        int select = c.getColumnIndex(GoalDatabaseHelper.COL4);

        int selectedi = c.getInt(select);
        int mGoalPriority = c.getInt(goalPriColumn);
        final int mGoalId = c.getInt(goalIDColumn);
        final String mGoalName = c.getString(goalNameColumn);
        final ImageButton mDeleteGoal = v.findViewById(R.id.goalClear);
        final TextView goalName = v.findViewById(R.id.goal_name);
        final RelativeLayout sv = v.findViewById(R.id.selected_view);
        if (goalName != null) {
            goalName.setText(mGoalName);
        }

        if (selectedi == 1){
            sv.setVisibility(View.INVISIBLE);
        }
        else if (selectedi == 0){
            sv.setVisibility(View.VISIBLE);
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = mDatabaseHelper.getSelect(mGoalId);
                if(c != null)
                { c.moveToFirst();
                }
                int selected = (c.getInt(c.getColumnIndex(GoalDatabaseHelper.COL4)));
                if (selected == 1){
                    View parent = (View) v.getParent();
                    mDatabaseHelper.updateSelected(0,mGoalId,1);
                    toastMessage("selected goal has been selected" + selected);
                    sv.setVisibility(View.INVISIBLE);
                    populateListNA(v);
                }
                else if (selected == 0){
                    View parent = (View) v.getParent();
                    mDatabaseHelper.updateSelected(1,mGoalId,0);
                    toastMessage("selected goal is" + selected);
                    sv.setVisibility(View.VISIBLE);
                    populateListNA(v);

                };
            }
        });


        mDeleteGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View parent = (View) view.getParent();
                final TextView goalName = parent.findViewById(R.id.goal_name);
                final Animation fadeOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out_q);
                parent.startAnimation(fadeOut);
                parent.setVisibility(View.GONE);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String goal_name = String.valueOf(goalName.getText());
                        Cursor data = mDatabaseHelper.getItemID(goal_name);
                        int itemID = -1;
                        while (data.moveToNext()) {
                            itemID = data.getInt(0);
                        }
                        mDatabaseHelper.deleteName(itemID, goal_name);
                        populateList(view);
                    }
                }, 250);

            }
        });

    }
    private void populateList(View v){
        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_in_small);

        mListviewg.setAnimation(animation);

        String[] fromColumns = mDatabaseHelper.databaseToStringArray();

        int[] toViews = new int[]{R.id.goal_name};

        Cursor cursor;

        cursor = mDatabaseHelper.getWritableDatabase().rawQuery("SELECT * FROM " + GoalDatabaseHelper.TABLE_NAME_G + " ORDER BY " + GoalDatabaseHelper.COL3 + " DESC", null);

        SimpleCursorAdapter customAdapter = new CustomGoalAdapter(v.getContext(), R.layout.list_item_goal, cursor, fromColumns, toViews, 0);

        mListviewg.setAdapter(customAdapter);
    }
    private void populateListNA(View v){

        String[] fromColumns = mDatabaseHelper.databaseToStringArray();

        int[] toViews = new int[]{R.id.goal_name};

        Cursor cursor;

        cursor = mDatabaseHelper.getWritableDatabase().rawQuery("SELECT * FROM " + GoalDatabaseHelper.TABLE_NAME_G + " ORDER BY " + GoalDatabaseHelper.COL3 + " DESC", null);

        SimpleCursorAdapter customAdapter = new CustomGoalAdapter(v.getContext(), R.layout.list_item_goal, cursor, fromColumns, toViews, 0);

        mListviewg.setAdapter(customAdapter);
    }

    private void toastMessage(String message){
        Toast.makeText(context ,message, Toast.LENGTH_SHORT).show();
    }


}
