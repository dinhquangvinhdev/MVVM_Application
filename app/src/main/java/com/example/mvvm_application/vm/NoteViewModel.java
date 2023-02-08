package com.example.mvvm_application.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvm_application.model.Note;
import com.example.mvvm_application.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void insert(Note note){
       noteRepository.insert(note);
    }

    public void updateNote(Note note){
        noteRepository.updateNote(note);
    }

    public void deleteNote(Note note){
        noteRepository.deleteNote(note);
    }

    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return noteRepository.getALlNotes();
    }
}
