package com.example.globalnotesapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var notesList = ArrayList<String>()
    fun updateNotesList(notes: ArrayList<String>){
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }
    class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteText: TextView = itemView.findViewById(R.id.tv_note_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_notes_item, parent, false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteText.text = notesList[position]
    }

    override fun getItemCount(): Int {
        Log.d("count", "${notesList.size}")
        return notesList.size
    }
}