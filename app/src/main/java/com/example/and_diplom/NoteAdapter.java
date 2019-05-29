package com.example.and_diplom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private List<Note> notes;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<Note> notes) {
        if (notes == null) {
            this.notes = new ArrayList<>();
        } else {
            this.notes = notes;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(Note item) {
        this.notes.add(item);
        Collections.sort(notes);
        notifyDataSetChanged();
    }

    public void editItem(Note noteOld, Note noteNew) {
        deleteItem(noteOld);
        addItem(noteNew);
    }

    public void deleteItem(Note noteOld) {
        this.notes.remove(noteOld);
        Collections.sort(notes);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < notes.size()) {
            return notes.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.note_item, parent, false);
        }

        Note note = notes.get(position);
        TextView title = view.findViewById(R.id.note_title);
        TextView text = view.findViewById(R.id.note_text);
        TextView deadline = view.findViewById(R.id.note_deadline);
        title.setText(note.getTitle());
        text.setText(note.getNote());
        deadline.setText(note.getDeadlineText());
        return view;
    }
}
