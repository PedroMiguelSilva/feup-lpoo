package com.damon.merakiapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;

import java.util.ArrayList;
import java.util.List;

public class ArchiveFragment extends Fragment {

    private List<Task> archivedTasks;
    private ListView listView;

    public ArchiveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);

        this.listView = (ListView) rootView.findViewById(R.id.list_archived_tasks);
        extractData();
        this.listView.setAdapter(ViewManager.getInstance().getArchiveAdapter());

        return rootView;
    }

    private void extractData() {
        int userId = DataManager.getInstance().getUserLogged().getId();
        this.archivedTasks = DataManager.getInstance().getDataBase().getUserTasks(userId, true);

        if (this.archivedTasks == null)
            this.archivedTasks = new ArrayList<>();

        ViewManager.getInstance().setArchiveAdapter(new ListAdapter(getContext(), this.archivedTasks));
    }

}
