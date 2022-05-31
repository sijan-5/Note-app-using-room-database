package com.example.room_database.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.room_database.R
import com.example.room_database.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_homefragment.*
import kotlinx.coroutines.launch


class Homefragment : BaseFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_add.setOnClickListener {
            val action = HomefragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)

           // view?.findNavController()?.navigate(R.id.action_add_note)

        }
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager =
            StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NoteDatabase.getDatabase(context!!).getnoteDao().getAllNotes()
                recycler_view.adapter = NotesAdapter(notes)

            }
        }
    }


}