package com.example.mvvm_application.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm_application.R;
import com.example.mvvm_application.adapter.NoteAdapter;
import com.example.mvvm_application.callback.ItemCallback;
import com.example.mvvm_application.databinding.ActivityMainBinding;
import com.example.mvvm_application.model.MConst;
import com.example.mvvm_application.model.Note;
import com.example.mvvm_application.vm.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemCallback {
    private ActivityMainBinding binding;
    private NoteViewModel noteVM;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initVM();
        initClick();
    }

    private void initClick() {
        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, MConst.ADD_NOTE_REQUEST);
            }
        });
    }

    private void supportRecycleView() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteVM.deleteNote(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.rvData);
    }

    private void initView() {
        //recycle view
        binding.rvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvData.setHasFixedSize(true);
        adapter = new NoteAdapter(MainActivity.this);
        binding.rvData.setAdapter(adapter);
        supportRecycleView();
    }

    private void initVM() {
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same NoteViewModel instance created by the first activity.
        noteVM = new ViewModelProvider(this).get(NoteViewModel.class);
        noteVM.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update new notes
                adapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MConst.ADD_NOTE_REQUEST: {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        String title = data.getStringExtra(MConst.KEY_NOTE_TITLE);
                        String description = data.getStringExtra(MConst.KEY_NOTE_DESCRIPTION);
                        int priority = data.getIntExtra(MConst.KEY_NOTE_PRIORITY, -1);

                        // insert note
                        noteVM.insert(new Note(title, description, priority));

                        //toast
                        Toast.makeText(this, "Add note success", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Add note failed", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case MConst.EDIT_NOTE_REQUEST:{
                if(resultCode == RESULT_OK){
                    if(data != null){
                        int id = data.getIntExtra(MConst.KEY_NOTE_ID, -1);
                        String title = data.getStringExtra(MConst.KEY_NOTE_TITLE);
                        String description = data.getStringExtra(MConst.KEY_NOTE_DESCRIPTION);
                        int priority = data.getIntExtra(MConst.KEY_NOTE_PRIORITY, -1);

                        if(id > 0){
                            Note tempNote = new Note(title, description , priority);
                            tempNote.setId(id);
                            noteVM.updateNote(tempNote);
                            Toast.makeText(this, "edit note success", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(this, "edit note failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteVM.deleteAllNotes();
                Toast.makeText(this, "delete all notes", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickItem(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(MConst.KEY_NOTE_ID, note.getId());
        intent.putExtra(MConst.KEY_NOTE_TITLE, note.getTitle());
        intent.putExtra(MConst.KEY_NOTE_DESCRIPTION, note.getDescription());
        intent.putExtra(MConst.KEY_NOTE_PRIORITY, note.getPriority());
        startActivityForResult(intent, MConst.EDIT_NOTE_REQUEST);
    }

    @Override
    public void onLongClickItem() {

    }
}