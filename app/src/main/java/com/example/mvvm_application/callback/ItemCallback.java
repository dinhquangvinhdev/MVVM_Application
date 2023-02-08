package com.example.mvvm_application.callback;

import com.example.mvvm_application.model.Note;

public interface ItemCallback {
    void onClickItem(Note note);
    void onLongClickItem();
}
