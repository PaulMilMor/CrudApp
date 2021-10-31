package com.josemillanes.crud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Activity context;
    private int layout;
    private ArrayList<Assignment> assignments;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private MyOpenHelper db;

    public MyAdapter(Activity context, int layout, ArrayList<Assignment> assignments) {
        this.context = context;
        this.layout = layout;
        this.assignments = assignments;
        db = new MyOpenHelper(context);
    }

    @Override
    public int getCount() {
        return this.assignments.size();
    }

    @Override
    public Object getItem(int position) {
        return this.assignments.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        View v = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_item,null);
        String currentTitle = assignments.get(position).getTitle();
        String currentDate = dateFormat.format(assignments.get(position).getDueDate());
        String currentSubject = assignments.get(position).getSubject();
        char currentCharacter = assignments.get(position).getCircleCharacter();
        ColorStateList currentColor = assignments.get(position).getChosenColor();

        TextView titleText = (TextView) v.findViewById(R.id.title_text);
        TextView dateText = (TextView) v.findViewById(R.id.date_text);
        TextView subjectText = (TextView) v.findViewById(R.id.subject_text);
        TextView charText = (TextView) v.findViewById(R.id.circle_letter);
        ImageButton optionsButton = (ImageButton) v.findViewById(R.id.options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMoreActionsMenu(view, assignments.get(position));
            }
        });

        titleText.setText(currentTitle);
        dateText.setText(currentDate);
        subjectText.setText(currentSubject);
        charText.setText(""+currentCharacter);
        charText.setBackgroundTintList(currentColor);
        return v;

    }

    private void showMoreActionsMenu(View view, Assignment assignment) {
        Context menuContext = this.context;
        PopupMenu optionsMenu = new PopupMenu(menuContext, view);
        optionsMenu.getMenuInflater().inflate(R.menu.options_menu,optionsMenu.getMenu());
        optionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.edit_button:
                        Intent intentForm = new Intent(menuContext, FormActivity.class);
                        intentForm.putExtra("assignment", assignment);
                        menuContext.startActivity(intentForm);

                        break;
                    case R.id.delete_button:
                        Toast.makeText(context,R.string.assignmentDeleted,Toast.LENGTH_SHORT).show();
                        db.delete(assignment.getId());
                        assignments.remove(assignment);
                        notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        optionsMenu.show();
    }

}
