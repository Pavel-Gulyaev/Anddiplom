package com.example.and_diplom;

import android.app.Dialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NoteActivity extends AppCompatActivity {

    public FloatingActionButton new_note_btn;
    private PinEditor pinEditor = new PinEditor(this);
    private boolean showed;
    private NoteEditor noteEditor;
    public NoteAdapter noteAdapter;
    public NoteFileEditor noteFileEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.pin_settings) {
            Dialog menu = onCreateMenu();
            menu.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initViews() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        new_note_btn = findViewById(R.id.new_note_btn);
        noteEditor = new NoteEditor(this);

        ListView listView = findViewById(R.id.list_note);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) noteAdapter.getItem(position);
                Dialog editNote = noteEditor.createNoteEdit(note);
                editNote.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) noteAdapter.getItem(position);
                Dialog deleteNote = noteEditor.createNoteDelete(note);
                deleteNote.show();
                return true;
            }
        });
        noteFileEditor = new NoteFileEditor(this);

        noteAdapter = new NoteAdapter(this, noteFileEditor.getNoteList());


        listView.setAdapter(noteAdapter);

        noteFileEditor.getNoteData();
        for (Note note : noteFileEditor.getNoteList()) {
            noteAdapter.addItem(note);

        }
        noteAdapter.notifyDataSetChanged();

        new_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog newNote = noteEditor.createNoteMenu();
                newNote.show();
            }
        });
        showed = false;

    }

    private Dialog onCreateMenu() {
        AlertDialog.Builder menuBuilder = new AlertDialog.Builder(NoteActivity.this);
        LayoutInflater inflater = NoteActivity.this.getLayoutInflater();
        View dialogViews = inflater.inflate(R.layout.pin_menu, null);
        menuBuilder.setView(dialogViews);
        final EditText newPin = (EditText) dialogViews.findViewById(R.id.new_pin);
        final ImageButton showBtn = dialogViews.findViewById(R.id.show_btn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showed) {
                    showBtn.setImageResource(R.drawable.ic_visibility_black_24dp);
                    newPin.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    newPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showed = !showed;
                } else {
                    showBtn.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    newPin.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    newPin.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                    showed = !showed;
                }
            }
        });
        menuBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pinText = newPin.getText().toString();
                if (pinEditor.ckeckNewPin(pinText)) {
                    pinEditor.saveNew(Integer.parseInt(pinText));
                    Toast.makeText(NoteActivity.this, "Новый pin " + pinText, Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
            }
        });
        menuBuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        InputMethodManager imm = (InputMethodManager)getSystemService(this.getBaseContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        return menuBuilder.create();
    }
}
