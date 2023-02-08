package com.example.mvvm_application.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase INSTANCE;
    // please remember noteDao parameter in this class is abstract
    // so please do not call noteDao -> you need to call INSTANCE and get noteDao
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_table"
            )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(INSTANCE.noteDao()).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void , Void>{
        private NoteDao noteDao;

        private  PopulateDBAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insertNote(new Note("title1", "des1", 1));
            noteDao.insertNote(new Note("title2", "des2", 1));
            noteDao.insertNote(new Note("title3", "des3", 2));
            noteDao.insertNote(new Note("title4", "des4", 3));
            return null;
        }
    }
}
