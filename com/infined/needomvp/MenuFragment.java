package com.infined.needomvp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MenuFragment extends Fragment {

    private GoalDatabaseHelper mDatabaseHelper;
    private ListView mListview;
    private int counter = 0;
    private int selectedposition;
    private TextView goalName;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_menu, container, false);
            goalName = v.findViewById(R.id.goal_name);

            mDatabaseHelper = new GoalDatabaseHelper(v.getContext());
            mListview = v.findViewById(R.id.goalList);

            return v;

    }
}
