package com.example.noteapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.viewmodel.NoteViewModel
import com.example.noteapp.R
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        addFragment()
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
        noteViewModel.readAllData.observe(viewLifecycleOwner) {
            notesAdapter.setData(it)
        }
    }

    private fun addFragment() {
        binding.btnAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, CreateNoteFragment()).addToBackStack(null).commit()
        }
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int) {
            val fragment = CreateNoteFragment()
            val bundle = Bundle()
            bundle.putInt("notesId", notesId)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.frameContainer, fragment)
                .addToBackStack(null).commit()
        }
    }

}