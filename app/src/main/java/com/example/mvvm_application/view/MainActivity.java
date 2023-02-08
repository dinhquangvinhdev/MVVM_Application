package com.example.mvvm_application.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm_application.R;
import com.example.mvvm_application.adapter.NoteAdapter;
import com.example.mvvm_application.databinding.ActivityMainBinding;
import com.example.mvvm_application.model.MConst;
import com.example.mvvm_application.model.Note;
import com.example.mvvm_application.vm.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, MConst.ADD_NOTE_REQUEST);
            }
        });
    }

    private void initView() {
        //recycle view
        binding.rvData.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        binding.rvData.setHasFixedSize(true);
        adapter = new NoteAdapter();
        binding.rvData.setAdapter(adapter);
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
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fab_add_note:

            default:
                Log.e("bibi", "not found id key");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MConst.ADD_NOTE_REQUEST:
                if(resultCode == Activity.RESULT_OK){
                    if(data != null){
                        String title = data.getStringExtra(MConst.KEY_NOTE_TITLE);
                        String description = data.getStringExtra(MConst.KEY_NOTE_DESCRIPTION);
                        int priority  = data.getIntExtra(MConst.KEY_NOTE_PRIORITY, -1);

                        // insert note
                        noteVM.insert(new Note(title, description, priority));
                    }
                }
                break;
            default:
                break;
        }
    }
}