package com.example.and_diplom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Comparable {
    private String title;
    private String note;

    private String id;

    private String deadlineText;
    private Date deadline;
    private Date lastEdit;

    public Note(String title, String note, String deadline) {
        this.title = title;
        this.note = note;
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        this.deadlineText = deadline;
        try {
            this.deadline = date.parse(deadline);
        } catch (Exception e) {

        }
        lastEdit = new Date(System.currentTimeMillis());
        id = String.valueOf(lastEdit.getTime());
    }

    public Note(String title, String note, String deadline, String id) {
        this.title = title;
        this.note = note;
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        this.deadlineText = deadline;
        try {
            this.deadline = date.parse(deadline);
        } catch (Exception e) {

        }
        lastEdit = new Date(System.currentTimeMillis());
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDeadlineText() {
        return deadlineText;
    }

    public String getId() {
        return id;
    }

    public void setDeadlineText(String deadliteText) {
        this.deadlineText = deadliteText;
    }


    public void setTitle(String title) {
        this.title = title;
        lastEdit = new Date(System.currentTimeMillis());
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        lastEdit = new Date(System.currentTimeMillis());
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDeadline(String deadline) {
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.deadline = date.parse(deadline);
        } catch (Exception e) {

        }
        lastEdit = new Date(System.currentTimeMillis());
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    @Override
    public int compareTo(Object o) {
        Note n = (Note) o;
        if (this.deadline == null) {
            if (n.deadline == null) {
                return (-1) * this.getLastEdit().compareTo(n.getLastEdit());
            } else {
                return (1);
            }
        }
        if (n.deadline == null) {
            return (-1);
        }
        if (this.getDeadline().compareTo(n.getDeadline()) == 0) {
            return (-1)*this.getLastEdit().compareTo(n.getLastEdit());
        } else {
            return this.getDeadline().compareTo(n.getDeadline());
        }

    }
}

