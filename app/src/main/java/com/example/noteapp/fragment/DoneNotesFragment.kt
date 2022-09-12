package com.example.noteapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.databinding.FragmentDoneNotesBinding
import com.example.noteapp.viewmodel.NoteViewModel


class DoneNotesFragment : Fragment(R.layout.fragment_done_notes) {


    private lateinit var binding: FragmentDoneNotesBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDoneNotesBinding.bind(view)
        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        notesAdapter = NotesAdapter {
            noteViewModel.updateNote(it)
        }
        binding.apply {
            rvTasks.adapter = notesAdapter
            rvTasks.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        notesAdapter.setOnClickListener(onClicked)
    }

    private fun initViewModel() {
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.doneNotes.observe(viewLifecycleOwner) {
            notesAdapter.setData(it)
        }
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int, notesStatus: Int) {
            val fragment = CreateNoteFragment()
            val bundle = Bundle()
            bundle.putInt("notesId", notesId)
            bundle.putInt("noteStatus", notesStatus)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.frameContainer, fragment)
                .addToBackStack(null).commit()
        }
    }

}