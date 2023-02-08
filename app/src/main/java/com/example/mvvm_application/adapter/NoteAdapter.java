package com.example.mvvm_application.adapter;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_application.callback.ItemCallback;
import com.example.mvvm_application.databinding.ItemNoteBinding;
import com.example.mvvm_application.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private ItemCallback callback;

    public NoteAdapter(ItemCallback callback){
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding itemView = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(notes.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemNoteBinding binding;
        private ItemCallback callback;

        public MyViewHolder(@NonNull ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Note note, ItemCallback callback){
            binding.tvTitle.setText(note.getTitle());
            binding.tvDescription.setText(note.getDescription());
            binding.tvPriority.setText(String.valueOf(note.getPriority()));
            this.callback = callback;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callback != null){
                        callback.onClickItem(note);
                    }
                }
            });
        }
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }
}
