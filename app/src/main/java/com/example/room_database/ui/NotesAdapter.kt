package com.example.room_database.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.room_database.R
import com.example.room_database.db.Note
import kotlinx.android.synthetic.main.for_notes.view.*

class NotesAdapter(private val notes : List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(val view : View) : RecyclerView.ViewHolder(view)
    {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.for_notes,parent,false)        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        holder.view.recycler_title.text = notes[position].title
        holder.view.recycler_discription.text = notes[position].note

        holder.view.setOnClickListener {
            val action = HomefragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount() = notes.size

}