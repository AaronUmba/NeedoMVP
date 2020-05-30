package com.infined.needomvp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CustomGoalSelectAdapter extends SimpleCursorAdapter {
    private int layout;
    private Context context;
    private GoalDatabaseHelper mGDatabaseHelper;
    private RelativeLayout goal_selected;
    private TextView goalName;

    public CustomGoalSelectAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
    }
    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup parent) {
        Cursor c = getCursor();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.goal_select_item, parent, false);
        bindView(v, context, c);

        mGDatabaseHelper = new GoalDatabaseHelper(v.getContext());

        return v;
    }
    @Override
    public void bindView(final View v, Context context, final Cursor c) {

        int goalIDColumn = c.getColumnIndex(GoalDatabaseHelper.COL1);
        int goalNameColumn = c.getColumnIndex(GoalDatabaseHelper.COL2);
        int goalPriColumn = c.getColumnIndex(GoalDatabaseHelper.COL3);

        final int mGoalPriority = c.getInt(goalPriColumn);
        final int mGoalId = c.getInt(goalIDColumn);
        String mGoalName = c.getString(goalNameColumn);
        goalName = v.findViewById(R.id.goal_select_name);
        if (goalName != null) {
            goalName.setText(mGoalName);
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
