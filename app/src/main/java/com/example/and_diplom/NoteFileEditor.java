package com.example.and_diplom;


import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class NoteFileEditor {
    private final static String NOTE_LIST_FILE = "noteListFile";
    private List<Note> noteList;
    private StringBreaker stringBreaker = new StringBreaker();

    public NoteFileEditor(NoteActivity activity) {
        this.activity = activity;
    }

    private NoteActivity activity;

    public List<Note> getNoteList() {
        return noteList;
    }

    public void saveNoteList() {
        try {
            FileOutputStream fileOutputStream = activity.openFileOutput(NOTE_LIST_FILE, activity.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            for (Note note : noteList) {
                bw.write(note.getId() + "\n");

            }

            bw.close();
        } catch (Exception e) {

        }
    }

    public void addNote(Note note) {
        noteList.add(note);
        try {
            FileOutputStream noteOutputStream = activity.openFileOutput(note.getId(), activity.MODE_PRIVATE);
            OutputStreamWriter noteStreamWriter = new OutputStreamWriter(noteOutputStream);
            BufferedWriter noteBw = new BufferedWriter(noteStreamWriter);
            noteBw.write(stringBreaker.stringAssotiation(note.getTitle()) + "\n");
            noteBw.write(note.getDeadlineText() + "\n");
            noteBw.write(stringBreaker.stringAssotiation(note.getNote()));
            noteBw.close();

        } catch (Exception e) {

        }
        saveNoteList();
    }

    public void editNote(Note noteOld, Note noteNew) {
        deleteNote(noteOld);
        addNote(noteNew);
    }

    public void deleteNote(Note noteOld) {
        noteList.remove(noteOld);
        saveNoteList();
    }

    public void getNoteData() {
        noteList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = activity.openFileInput(NOTE_LIST_FILE);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            while (true) {
                String noteID = reader.readLine();

                if (noteID.equals("")) {
                    break;
                }
                try {
                    FileInputStream noteStream = activity.openFileInput(noteID);
                    InputStreamReader noteReader = new InputStreamReader(noteStream);
                    BufferedReader noteBuffReader = new BufferedReader(noteReader);
                    String noteTitle = stringBreaker.stringBreak(noteBuffReader.readLine());
                    String noteDeadline = noteBuffReader.readLine();
                    String noteText = stringBreaker.stringBreak(noteBuffReader.readLine());


                    noteList.add(new Note(noteTitle, noteText, noteDeadline, noteID));

                } catch (Exception e) {

                }


            }


        } catch (Exception e) {

        }
    }


}
