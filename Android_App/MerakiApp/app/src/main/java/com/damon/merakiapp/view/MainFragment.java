package com.damon.merakiapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.damon.merakiapp.R;

public class MainFragment extends Fragment {

    private ExpandableListView listView;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ExpandableListView) rootView.findViewById(R.id.main_list);
        ViewManager.getInstance().setExpandableListAdapter(new ExpandableListAdapter(rootView.getContext(), ViewManager.getInstance().getListDataHeader(), ViewManager.getInstance().getListHash()));
        listView.setAdapter(ViewManager.getInstance().getExpandableListAdapter());

        return rootView;
    }

}
