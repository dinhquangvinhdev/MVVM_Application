package com.example.mvvm_application.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm_application.R;
import com.example.mvvm_application.databinding.ActivityEditNoteBinding;
import com.example.mvvm_application.model.MConst;
import com.example.mvvm_application.model.Note;

public class EditNoteActivity extends AppCompatActivity {
    private ActivityEditNoteBinding binding;
    private Note tempNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        getDataFromIntent();
    }

    private void initView() {
        binding.numberPickerPriority.setMinValue(1);
        binding.numberPickerPriority.setMaxValue(10);
    }

    private void getDataFromIntent() {
        Intent data = getIntent();
        if(data != null){
            int id = data.getIntExtra(MConst.KEY_NOTE_ID, -1);
            String title = data.getStringExtra(MConst.KEY_NOTE_TITLE);
            String description = data.getStringExtra(MConst.KEY_NOTE_DESCRIPTION);
            int priority = data.getIntExtra(MConst.KEY_NOTE_PRIORITY, -1);
            //set new temp note
            tempNote = new Note(title , description , priority);
            tempNote.setId(id);
            if(id == -1){
                return ;
            } else {
                updateUI(title, description, priority);
            }
        }
    }

    private void updateUI(String title, String description, int priority) {
        binding.edtTitle.setText(title);
        binding.edtDescription.setText(description);
        binding.numberPickerPriority.setValue(priority);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = binding.edtTitle.getText().toString();
        String description = binding.edtDescription.getText().toString();
        int priority = binding.numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        //create intent to send data back
        Intent intent = new Intent();
        intent.putExtra(MConst.KEY_NOTE_ID, tempNote.getId());
        intent.putExtra(MConst.KEY_NOTE_TITLE, title);
        intent.putExtra(MConst.KEY_NOTE_DESCRIPTION, description);
        intent.putExtra(MConst.KEY_NOTE_PRIORITY, priority);
        setResult(RESULT_OK, intent);
        finish();
    }
}