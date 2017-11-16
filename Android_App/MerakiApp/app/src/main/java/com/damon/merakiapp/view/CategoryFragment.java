package com.damon.merakiapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.damon.merakiapp.R;

public class CategoryFragment extends Fragment {

    private GridView gridView;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        if (container == null)
            return null;

        this.gridView = (GridView) rootView.findViewById(R.id.gridview);
        ViewManager.getInstance().setGridAdapter(new GridAdapter(rootView.getContext()));
        this.gridView.setAdapter(ViewManager.getInstance().getGridAdapter());

        return rootView;
    }

}