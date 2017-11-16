package com.damon.merakiapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Task>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Task>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (listHashMap.get(listDataHeader.get(i)) == null)
            return 0;
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView lblListHeader = (TextView) view.findViewById(R.id.main_list_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final Task childTask = (Task) getChild(i, i1);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) view.findViewById(R.id.main_list_item);
        txtListChild.setText(childTask.getText());

        createCheckBox(view, childTask);
        createButton(view, childTask);

        return view;
    }

    private void createCheckBox(View view, final Task task) {
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {
                task.setComplete(checkBox.isChecked());
                DataManager.getInstance().getDataBase().updateTask(task);
            }
        });

        if (task.isComplete())
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);
    }

    private void createButton(View view, final Task task) {
        Button mButton = (Button) view.findViewById(R.id.button_edit);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ViewManager.getInstance().setManageTask(true);
                ViewManager.getInstance().setTaskToEdit(task);

                Intent intent = new Intent();
                intent.setClass(view.getContext(), ManageTaskActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void updateAdapter(List<String> listDataHeader, HashMap<String, List<Task>> listHashMap) {
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
        notifyDataSetChanged();
    }
}