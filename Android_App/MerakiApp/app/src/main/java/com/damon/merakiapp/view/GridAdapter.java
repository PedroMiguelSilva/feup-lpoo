package com.damon.merakiapp.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.Category;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categories;

    public GridAdapter(Context context) {
        this.context = context;
        ArrayList<Category> userCategories = DataManager.getInstance().getDataBase().getUserCategories(DataManager.getInstance().getUserLogged().getId());

        if (userCategories != null)
            this.categories = userCategories;
        else this.categories = new ArrayList<Category>();
    }

    @Override
    public int getCount() {
        if (this.categories == null)
            return 0;
        return this.categories.size();
    }

    @Override
    public Category getItem(int position) {
        return this.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Category category = this.categories.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_button, null);
        }

        TextView categoryName = (TextView) convertView.findViewById(R.id.category_name);
        TextView numberOfTasks = (TextView) convertView.findViewById(R.id.number_tasks);

        ArrayList<Task> catTasks = DataManager.getInstance().getDataBase().getCategoryTasks(category.getId());

        categoryName.setText(category.getName());

        if (catTasks == null)
            numberOfTasks.setText("0 Tasks");
        else {
            if (catTasks.size() == 1)
                numberOfTasks.setText("1 Task");
            else numberOfTasks.setText("" + catTasks.size() + " Tasks");
        }

        final ImageButton button = (ImageButton) convertView.findViewById(R.id.category_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewManager.getInstance().setCurrentCategory(category);
                ViewManager.getInstance().setArchiveView(false);
                Intent intent = new Intent(view.getContext(), CategoryTasksActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public void updateAdapter() {

        ArrayList<Category> userCategories = DataManager.getInstance().getDataBase().getUserCategories(DataManager.getInstance().getUserLogged().getId());

        if (userCategories != null)
            this.categories = userCategories;
        else this.categories = new ArrayList<Category>();

        notifyDataSetChanged();
    }
}
