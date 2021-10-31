package com.josemillanes.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;;

import java.util.ArrayList;
import java.util.Date;

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String ASSIGNMENTS_TABLE_CREATE = "CREATE TABLE assignments (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, duedate INTEGER, subject TEXT)";
    private static final String DB_NAME = "assignments.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private Context myContext;

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ASSIGNMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String title, Date dueDate, String subject) {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("duedate",dueDate.getTime());
        cv.put("subject",subject);
        db.insert("assignments",null,cv);
    }

    public void update(int id, String title, Date dueDate, String subject) {
        ContentValues cv = new ContentValues();
        cv.put("_id",id);
        cv.put("title", title);
        cv.put("duedate",dueDate.getTime());
        cv.put("subject",subject);
        String whereClause = "_id=?";
        String whereArgs[] = {""+id};
        db.update("assignments", cv, whereClause, whereArgs);
    }

    public void delete(int id) {
        String[] args = new String[]{String.valueOf(id)};
        db.delete("assignments","_id=?", args);
    }

    public ArrayList<Assignment> getAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        Cursor c = db.rawQuery("select _id, title, duedate, subject from assignments", null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {

                int id = c.getInt(0);
                String title = c.getString(1);
                Date dueDate = new Date(c.getLong(2));
                String subject = c.getString(3);
                Assignment assignment = new Assignment(id, title, dueDate, subject);
                assignments.add(assignment);
            } while(c.moveToNext());
        }
        c.close();
        return assignments;
    }


}
