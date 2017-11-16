package com.damon.merakiapp.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<Task> items;

    public ListAdapter(Context context, List<Task> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        if (this.items == null)
            return 0;
        return items.size();
    }

    @Override
    public Task getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Task task = items.get(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.main_list_item);
        textView.setText(task.getText());
        createCheckBox(view, task);
        createButton(view, task);

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

        if (ViewManager.getInstance().isArchiveView())
            checkBox.setVisibility(View.GONE);
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

    public void updateAdapter(List<Task> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}