package com.example.jek.notes4apalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.jek.notes4apalon.adapter.NotesAdapter;
import com.example.jek.notes4apalon.model.Constants;
import com.example.jek.notes4apalon.model.Note;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private List<Note> notes;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycleView();
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton btnCreateNote = findViewById(R.id.addNewNote);

        adapter = new NotesAdapter();
        mRealm = Realm.getInstance(MainActivity.this);
        updateData();

        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EditNote.class);
                intent.putExtra(Constants.NOTE_ID, "");
                startActivity(intent);
            }
        });

        NotesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getBaseContext(), EditNote.class);
                Note note = notes.get(position);
                intent.putExtra(Constants.NOTE_ID, note.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.rvNotes);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                mRealm.beginTransaction();
                Note note = notes.get(viewHolder.getAdapterPosition());
                note.removeFromRealm();
                mRealm.commitTransaction();
                updateData();
            } else {
                Intent intent = new Intent(getBaseContext(), EditNote.class);
                Note note = notes.get(viewHolder.getAdapterPosition());
                intent.putExtra(Constants.NOTE_ID, note.getId());
                startActivity(intent);
            }
        }
    };

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void updateData() {
        notes = mRealm.where(Note.class).findAll();
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }
}
