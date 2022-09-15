package com.example.noteapp.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        addFragment()
        initAdapter()
        initViewModel()
        setListener()
    }

    private fun setListener() {
        binding.ivMore.setOnClickListener {
            PopupMenu(requireContext(), it).apply {
                setOnMenuItemClickListener(this@HomeFragment)
                inflate(R.menu.action_menu)
                show()
            }
        }
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
        noteViewModel.homeNotes.observe(viewLifecycleOwner) {
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
        override fun onClicked(notesId: Int, notesStatus: Int) {
            val fragment = CreateNoteFragment()
            val bundle = Bundle()
            bundle.putInt("notesId", notesId)
            bundle.putInt("noteStatus", notesStatus)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.frameContainer, fragment)
                .addToBackStack(null).commit()
        }
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        return when (p0!!.itemId) {
            R.id.sortByTitle -> {
                sortByTitle()
                true
            }
            R.id.sortByDateTime -> {
                sortByDateTime()
                true
            }
            else -> false
        }
    }

    private fun sortByDateTime() {
        noteViewModel.homeNotes.observe(viewLifecycleOwner) { notes ->
            notes.sortBy { it.dateTime }
            notesAdapter.setData(notes)
        }
    }

    private fun sortByTitle() {
        noteViewModel.homeNotes.observe(viewLifecycleOwner) { notes ->
            notes.sortBy { it.title }
            notesAdapter.setData(notes)
        }
    }


}