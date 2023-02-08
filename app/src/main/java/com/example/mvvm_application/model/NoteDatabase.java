package com.example.mvvm_application.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvvm_application.NoteDao;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase INSTANCE;
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_table"
            )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
