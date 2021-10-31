package com.josemillanes.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etSubject;
    private EditText etDueDate;
    private Date selectedDate;
    private Button createAssignment;
    private MyOpenHelper db;
    private Assignment editedAssignment;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        db = new MyOpenHelper(this);

        etTitle = (EditText) findViewById(R.id.edit_title);
        etSubject = (EditText) findViewById(R.id.edit_subject);

        createAssignment = (Button) findViewById(R.id.form_button);
        createAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(editedAssignment == null) {
                   db.insert(etTitle.getText().toString(),selectedDate,etSubject.getText().toString());
                   db.getAssignments();
                   Toast.makeText(FormActivity.this,R.string.assignmentSaved,Toast.LENGTH_SHORT).show();
               } else {
                   editedAssignment.setTitle(etTitle.getText().toString());
                   editedAssignment.setDueDate(selectedDate);
                   editedAssignment.setSubject(etSubject.getText().toString());
                   db.update(editedAssignment.getId(),editedAssignment.getTitle(), editedAssignment.getDueDate(), editedAssignment.getSubject());
                   Toast.makeText(FormActivity.this,R.string.assignmentEdited, Toast.LENGTH_SHORT).show();
               }
                onBackPressed();
            }
        });

        etDueDate = (EditText) findViewById(R.id.edit_duedate);




        etDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                if(editedAssignment != null) {
                    cal.setTime(editedAssignment.getDueDate());
                }
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String selectedDateText = i2 + "/" + (i1+1) + "/" + i;
                        cal.set(i,i1,i2,0,0);
                        selectedDate = cal.getTime();
                        etDueDate.setText(selectedDateText);
                    }
                },currentYear,currentMonth,currentDay);
                datePickerDialog.show();
            }

        });

        Intent intent = getIntent();
        editedAssignment = (Assignment) intent.getSerializableExtra("assignment");

        if(editedAssignment != null) {
            etTitle.setText(editedAssignment.getTitle());
            etSubject.setText(editedAssignment.getSubject());
            etDueDate.setText(dateFormat.format(editedAssignment.getDueDate()));
            selectedDate = editedAssignment.getDueDate();
            createAssignment.setText(R.string.editassignment);
        }
    }
}