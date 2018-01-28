package com.example.jek.notes4apalon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.jek.notes4apalon.model.Constants;
import com.example.jek.notes4apalon.model.Note;

import java.util.UUID;

import io.realm.Realm;

public class EditNote extends AppCompatActivity {
    EditText title;
    EditText body;
    Note note;
    FloatingActionButton saveNote;
    FloatingActionButton deleteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.editTitle);
        body = findViewById(R.id.editBody);
        deleteNote = findViewById(R.id.deleteNote);

        saveNote = findViewById(R.id.saveNote);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateStorage())
                    finish();
            }
        });

        final Realm realm = Realm.getInstance(this);
        if (!getIntent().getStringExtra(Constants.NOTE_ID).isEmpty()) {
            String noteId = getIntent().getStringExtra((Constants.NOTE_ID));
            note = realm.where(Note.class).equalTo("id", noteId).findFirst();
            title.setText(note.getTitle());
            body.setText(note.getBody());
            deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    realm.beginTransaction();
                    note.removeFromRealm();
                    realm.commitTransaction();
                    finish();
                }
            });
        } else {
            deleteNote.hide();
        }
    }

    private boolean updateStorage() {
        String title = this.title.getText().toString().trim();
        String content = this.body.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            return false;
        }

        Realm realm = Realm.getInstance(getApplicationContext());

        if (getIntent().getStringExtra(Constants.NOTE_ID).isEmpty()) {
            realm.beginTransaction();
            note = realm.createObject(Note.class);
            note.setId(UUID.randomUUID().toString());
            realm.commitTransaction();
        }

        realm.beginTransaction();
        note.setTitle(title);
        note.setBody(content);
        realm.commitTransaction();
        return true;
    }
}
