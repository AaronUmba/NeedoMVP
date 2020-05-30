package com.infined.needomvp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetListener mListener;

    public static ListView mListviewg;
    private GoalDatabaseHelper mDatabaseHelper;
    private TextView menuName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        RelativeLayout addG = v.findViewById(R.id.GoalAddLayout);
        mDatabaseHelper = new GoalDatabaseHelper(v.getContext());
        mListviewg = v.findViewById(R.id.goalList);
        menuName = (TextView) v.findViewById(R.id.MenuName);
        addG.setOnClickListener(this);

        String username = null;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        username = prefs.getString("username", username);

        if (username != null){
            menuName.setText("Welcome " + username );
        } else {
            menuName.setText("Welcome. Here's what needs doing.");
        }

        populateGoalView();

        return v;
    }

        public void populateGoalView(){
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_small);
            final Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_q);

            mListviewg.setAnimation(animation);

            String[] fromColumns = mDatabaseHelper.databaseToStringArray();

            int[] toViews = new int[]{R.id.goal_name};

            Cursor cursor;

            cursor = mDatabaseHelper.getWritableDatabase().rawQuery("SELECT * FROM " + GoalDatabaseHelper.TABLE_NAME_G + " ORDER BY " + GoalDatabaseHelper.COL3 + " DESC", null);

            SimpleCursorAdapter customAdapter = new CustomGoalAdapter(getContext(), R.layout.list_item_goal, cursor, fromColumns, toViews,0);

            mListviewg.setAdapter(customAdapter);

        }
    private void toastMessage(String message){
        Toast.makeText(getContext() ,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.GoalAddLayout:
                Intent i = new Intent(getActivity(), GoalActivity.class);
                startActivity(i);

        }
    }
    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

}
