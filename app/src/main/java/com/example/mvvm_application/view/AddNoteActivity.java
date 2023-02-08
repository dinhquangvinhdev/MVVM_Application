package com.example.mvvm_application.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mvvm_application.R;
import com.example.mvvm_application.databinding.ActivityAddNoteBinding;
import com.example.mvvm_application.databinding.ActivityMainBinding;
import com.example.mvvm_application.model.MConst;
import com.example.mvvm_application.model.Note;

public class AddNoteActivity extends AppCompatActivity {
    private ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.numberPickerPriority.setMinValue(1);
        binding.numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
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

        Intent intent = new Intent();
        intent.putExtra(MConst.KEY_NOTE_TITLE, title);
        intent.putExtra(MConst.KEY_NOTE_DESCRIPTION, description);
        intent.putExtra(MConst.KEY_NOTE_PRIORITY, priority);
        setResult(RESULT_OK, intent);
        //end activity
        finish();
    }
}