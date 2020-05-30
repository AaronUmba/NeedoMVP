package com.infined.needomvp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CustomAdapter extends SimpleCursorAdapter {

    private int layout;
    private Context context;
    private DatabaseHelper mDatabaseHelper;
    private int id;
    private String name;

    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View newView(Context context,final Cursor cursor, ViewGroup parent) {
        Cursor c = getCursor();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item, parent, false);
        bindView(v, context, c);
        return v;
    }

    @Override
    public void bindView(View v, final Context context, Cursor c) {

        int taskNameColumn = c.getColumnIndex(DatabaseHelper.COL2);
        int taskDescColumn = c.getColumnIndex(DatabaseHelper.COL4);
        int taskIDColumn = c.getColumnIndex(DatabaseHelper.COL1);
        int taskPriColumn = c.getColumnIndex(DatabaseHelper.COL3);
        int taskBar1 = c.getColumnIndex(DatabaseHelper.COL6);
        int taskBar2 = c.getColumnIndex(DatabaseHelper.COL7);
        int taskBar3 = c.getColumnIndex(DatabaseHelper.COL8);

        int bar1 = c.getInt(taskBar1);
        int bar2 = c.getInt(taskBar2);
        int bar3 = c.getInt(taskBar3);
        int mTaskPriority = c.getInt(taskPriColumn);
        final int mTaskId = c.getInt(taskIDColumn);
        final String mTaskName = c.getString(taskNameColumn);
        String mTaskDesc = c.getString(taskDescColumn);

        name = mTaskName;
        id = mTaskId;
        CardView cv = v.findViewById(R.id.cv);

        final Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/SourceSansPro-SemiBold.ttf");
        final Typeface regface = Typeface.createFromAsset(context.getAssets(),
                "fonts/SourceSansPro-Regular.ttf");
        final Typeface smallface = Typeface.createFromAsset(context.getAssets(),
                "fonts/SourceSansPro-Light.ttf");

        //set the name of the entry
        TextView taskName = v.findViewById(R.id.task_name);
        View circle = v.findViewById(R.id.viewCircle);
        if (taskName != null){
            taskName.setText(mTaskName);
        }
        TextView taskDesc = v.findViewById(R.id.task_desc);
        if (taskDesc != null){
            taskDesc.setText(mTaskDesc);
        }

        String td = taskDesc.getText().toString();
        if (bar1 >= 9){
            cv.setCardBackgroundColor(0xDC6F98FF);
            taskName.setTextColor(0xffffffff);
            taskDesc.setTextColor(0xffffffff);
            circle.setBackgroundResource(R.layout.round_white_light);
        }
        if (bar2 >= 9){
            cv.setCardBackgroundColor(0xDC6F98FF);
            taskName.setTextColor(0xffffffff);
            taskDesc.setTextColor(0xffffffff);
            circle.setBackgroundResource(R.layout.round_white_light);
        }
        if (bar3 >= 9){
            cv.setCardBackgroundColor(0xDC6F98FF);
            taskName.setTextColor(0xffffffff);
            taskDesc.setTextColor(0xffffffff);
            circle.setBackgroundResource(R.layout.round_white_light);
        }
        if (mTaskPriority >= 125) {
            taskName.setTextColor(0xffffffff);
            taskDesc.setTextColor(0xffffffff);
            cv.setCardBackgroundColor(0xff4f7ffa);
            circle.setBackgroundResource(R.layout.round_white);
        }
        if (td.length() == 0){
            taskDesc.setVisibility(View.GONE);
        }
    }
}