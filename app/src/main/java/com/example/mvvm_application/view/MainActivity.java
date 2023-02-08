package com.example.mvvm_application.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mvvm_application.databinding.ActivityMainBinding;
import com.example.mvvm_application.model.Note;
import com.example.mvvm_application.vm.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoteViewModel noteVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initVM();
    }

    private void initView() {

    }

    private void initVM() {
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same NoteViewModel instance created by the first activity.
        noteVM = new ViewModelProvider(this).get(NoteViewModel.class);
        noteVM.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Toast.makeText(MainActivity.this, notes.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}