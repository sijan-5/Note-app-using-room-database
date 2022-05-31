package com.example.room_database.ui

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.room_database.R
import com.example.room_database.db.Note
import com.example.room_database.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    private var note : Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            title.setText(note?.title)
            input_note.setText(note?.note)
        }

       save_button.setOnClickListener { _ ->
           val note_title = title.text.toString().trim()
           val note_discription = input_note.text.toString().trim()

           if (note_title.isEmpty())
           {
               title.error = "title required"
               title.requestFocus()
               return@setOnClickListener
           }
           if (note_discription.isEmpty())
           {
               input_note.error = "Note required"
               input_note.requestFocus()
               return@setOnClickListener
           }

           launch {

               context?.let {
                   val mNote = Note(note_title,note_discription)

                   if (note== null)
                   {
                       NoteDatabase.getDatabase(context!!).getnoteDao().addNote(mNote)
                       Toast.makeText(activity,"Saved successfully",
                           Toast.LENGTH_LONG).show()
                   }
                   else
                   {
                       mNote.id = note!!.id
                       NoteDatabase.getDatabase(context!!).getnoteDao().updateNote(mNote)
                       Toast.makeText(activity,"Updated successfully",
                           Toast.LENGTH_LONG).show()

                   }



                   val action = AddNoteFragmentDirections.actionsavenote()
                   Navigation.findNavController(view!!).navigate(action)
               }
           }

       }
    }

    private fun deleteNote()
    {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation once it is done")
            setPositiveButton("Yes") {
                _,_ ->
                launch {
                    NoteDatabase.getDatabase(context!!).getnoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionsavenote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No")
            {
                _,_ ->
            }
        }.create().show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.delete -> if(note!= null) deleteNote() else {
                Toast.makeText(activity, "Items cannot be deleted", Toast.LENGTH_LONG).show()
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    }
