package com.josemillanes.crud;

import android.content.res.ColorStateList;
import android.graphics.Color;

import java.io.Serializable;
import java.util.Date;

public class Assignment implements Serializable {

    private int id;
    private String title;
    private Date dueDate;
    private String subject;
    private char circleCharacter;
    private transient ColorStateList chosenColor;

    public Assignment(int id, String title, Date dueDate, String subject) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.subject = subject;
        this.circleCharacter = subject.charAt(0);
        this.chosenColor =generateColorList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {

        this.subject = subject;
        this.circleCharacter = subject.charAt(0);
        this.chosenColor =generateColorList();
    }

    public char getCircleCharacter() {
        return circleCharacter;
    }

    public void setCircleCharacter(char circleCharacter) {
        this.circleCharacter = circleCharacter;
    }

    public ColorStateList getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(ColorStateList chosenColor) {
        this.chosenColor = chosenColor;
    }

    private int generateColor() {
        int colors[] = new int[] {
                Color.argb(180,247,125,17),
                Color.argb(180,180,196,53),
                Color.argb(180,47,134,153),
                Color.argb(180,227,211,70),
                Color.argb(180,19,78,240),
                Color.argb(180,27,82,92),
                Color.argb(180,153,71,35)

        };
        return colors[Math.abs(subject.hashCode()) % colors.length];
    }

    private ColorStateList generateColorList() {
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                generateColor(),
                Color.RED,
                Color.GREEN,
                Color.BLUE
        };
        return new ColorStateList(states, colors);
    }

}
