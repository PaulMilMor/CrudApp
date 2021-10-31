package com.josemillanes.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private ListView listview;
    private ArrayList<Assignment> assignments;
    private FloatingActionButton addAssignmentButton;
    private MyOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyOpenHelper(this);

        listview = (ListView) findViewById(R.id.list);
        assignments = db.getAssignments();
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, assignments);
        listview.setEmptyView(findViewById(R.id.empty));
        listview.setAdapter(myAdapter);

        addAssignmentButton = (FloatingActionButton) findViewById(R.id.add_assignment_button);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForm = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentForm);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        assignments = db.getAssignments();
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, assignments);
        listview.setAdapter(myAdapter);

    }
}