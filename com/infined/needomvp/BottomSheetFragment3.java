package com.infined.needomvp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BottomSheetFragment3 extends BottomSheetFragment{
    private BottomSheetFragment.BottomSheetListener mListener;

    private ListView mListView;
    private GoalDatabaseHelper mDatabaseHelper;
    private DatabaseHelper mDatabaseHelperMain;
    private int priority = 0;
    private int itemID = -1;
    private TextView goalName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goal_select, container, false);

        mListView = v.findViewById(R.id.goalListS);

        mDatabaseHelper = new GoalDatabaseHelper(v.getContext());

        mDatabaseHelperMain = new DatabaseHelper(v.getContext());

        String[] fromColumns = mDatabaseHelper.databaseToStringArray();

        int[] toViews = new int[]{R.id.goal_select_name};

        Cursor cursor;

        cursor = mDatabaseHelper.getWritableDatabase().rawQuery("SELECT * FROM " + GoalDatabaseHelper.TABLE_NAME_G + " ORDER BY " + GoalDatabaseHelper.COL1 + " DESC", null);

        SimpleCursorAdapter customAdapter = new CustomGoalSelectAdapter(getContext(), R.layout.goal_select_item, cursor, fromColumns, toViews,0);

        mListView.setAdapter(customAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                goalName = view.findViewById(R.id.goal_select_name);

                String name = goalName.getText().toString();

                Cursor data = mDatabaseHelper.getItemID(name);
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                Cursor pri = mDatabaseHelper.getGoalPriority(itemID);
                while(pri.moveToNext()){
                    priority = pri.getInt(0);
                }

                goalName = view.findViewById(R.id.goal_select_name);

                Intent intent = new Intent(getActivity().getBaseContext(),
                        MainActivity.class);

                intent.putExtra("ID", itemID);

                intent.putExtra("PRI", priority);

                getActivity().startActivity(intent);

                Log.i("BottomSheetFragment3","Selected goal priority is " + priority );

            }
        });

        return v;
    }
    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetFragment.BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener2");
        }
    }
}

