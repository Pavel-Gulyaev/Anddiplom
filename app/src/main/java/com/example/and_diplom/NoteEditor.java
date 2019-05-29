package com.example.and_diplom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;

public class NoteEditor {

    private NoteActivity activity;


    public NoteEditor(NoteActivity activity) {
        this.activity = activity;
    }

    public Dialog createNoteMenu() {
        AlertDialog.Builder noteBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogViews = inflater.inflate(R.layout.note_menu, null);
        noteBuilder.setView(dialogViews);
        final EditText title = dialogViews.findViewById(R.id.title);
        final EditText note = dialogViews.findViewById(R.id.note);
        final MaterialCheckBox deadlineCheckbox = dialogViews.findViewById(R.id.deadline_checkbox);

        final EditText deadline = dialogViews.findViewById(R.id.deadline_date);
        final ImageButton deadlineBtn = dialogViews.findViewById(R.id.deadline_btn);
        deadline.setEnabled(false);
        deadlineCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                deadlineBtn.setClickable(isChecked);
                if (!(isChecked)){
                    deadline.setText("");
                }

            }
        });
        deadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar todayCalendar = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                deadline.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

                            }
                        },
                        todayCalendar.get(Calendar.YEAR),
                        todayCalendar.get(Calendar.MONTH),
                        todayCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        noteBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String titleText = title.getText().toString();
                String noteText = note.getText().toString();
                String deadlineDate = deadline.getText().toString();
                Note note = new Note(titleText, noteText, deadlineDate);
                activity.noteAdapter.addItem(note);
                activity.noteFileEditor.addNote(note);

            }
        });
        noteBuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        return noteBuilder.create();
    }

    public Dialog createNoteEdit(final Note note) {
        AlertDialog.Builder noteBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogViews = inflater.inflate(R.layout.note_menu, null);
        noteBuilder.setView(dialogViews);
        final EditText title = dialogViews.findViewById(R.id.title);
        final EditText text = dialogViews.findViewById(R.id.note);
        final MaterialCheckBox deadlineCheckbox = dialogViews.findViewById(R.id.deadline_checkbox);

        final EditText deadline = dialogViews.findViewById(R.id.deadline_date);
        final ImageButton deadlineBtn = dialogViews.findViewById(R.id.deadline_btn);
        deadline.setEnabled(false);
        title.setText(note.getTitle());
        text.setText(note.getNote());
        deadline.setText(note.getDeadlineText());
        deadlineCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                deadlineBtn.setClickable(isChecked);
                if (!(isChecked)){
                    deadline.setText("");
                }

            }
        });
        deadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar todayCalendar = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                deadline.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

                            }
                        },
                        todayCalendar.get(Calendar.YEAR),
                        todayCalendar.get(Calendar.MONTH),
                        todayCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        noteBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String titleText = title.getText().toString();
                String noteText = text.getText().toString();
                String deadlineDate = deadline.getText().toString();
                Note noteNew = new Note(titleText, noteText, deadlineDate, note.getId());
                activity.noteAdapter.editItem(note, noteNew);
                activity.noteFileEditor.editNote(note, noteNew);

            }
        });
        noteBuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return noteBuilder.create();
    }

    public Dialog createNoteDelete(final Note note) {
        AlertDialog.Builder noteBuilder = new AlertDialog.Builder(activity);
        noteBuilder.setTitle(R.string.note_delete);
        noteBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.noteAdapter.deleteItem(note);
                activity.noteFileEditor.deleteNote(note);

            }
        });
        noteBuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return noteBuilder.create();
    }


}
