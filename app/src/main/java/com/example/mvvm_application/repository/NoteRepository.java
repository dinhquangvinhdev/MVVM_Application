package com.example.mvvm_application.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.mvvm_application.NoteDao;
import com.example.mvvm_application.model.Note;
import com.example.mvvm_application.model.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
        // noteDao.insertNote(note);
    }

    public void updateNote(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
        // noteDao.updateNote(note);
    }

    public void deleteNote(int id){
        new DeleteNoteAsyncTask(noteDao).execute(id);
        // noteDao.deleteNote(id);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
        // noteDao.deleteAllNotes();
    }

    public LiveData<List<Note>> getALlNotes(){
        return noteDao.getALlNotes();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void , Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void , Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Integer, Void , Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            noteDao.deleteNote(integers[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void , Void> {
        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
