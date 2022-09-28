package com.example.noteapp.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.broadcast.MESSAGE_EXTRA
import com.example.noteapp.broadcast.NOTE_ID
import com.example.noteapp.broadcast.Notification
import com.example.noteapp.broadcast.TITLE_EXTRA
import com.example.noteapp.databinding.FragmentGarbageBinding
import com.example.noteapp.viewmodel.NoteViewModel

@RequiresApi(Build.VERSION_CODES.O)
class GarbageFragment : Fragment(R.layout.fragment_garbage), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: FragmentGarbageBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGarbageBinding.bind(view)
        initAdapter()
        initViewModel()
        setListener()
    }

    private fun setListener() {
        binding.apply {
            ivMore.setOnClickListener {
                PopupMenu(requireContext(), it).apply {
                    setOnMenuItemClickListener(this@GarbageFragment)
                    inflate(R.menu.action_menu)
                    show()
                }
            }
            svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    setDataSearch(p0)
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    setDataSearch(p0)
                    return true
                }
            })
        }
    }

    private fun setDataSearch(p0: String?) {
        val tmp = "%$p0%"
        noteViewModel.searchByTitle(tmp, -1).observe(viewLifecycleOwner) { notes ->
            notesAdapter.setData(notes)
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
        noteViewModel.garbageNotes.observe(viewLifecycleOwner) {
            for (note in it) {
                cancelNotification(note.id!!, note.title!!, note.noteText!!, note.dateTime!!)
            }
            notesAdapter.setData(it)
        }
    }

    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int, notesStatus: Int, notesSubTitle: String) {
            val fragment = CreateNoteFragment()
            val bundle = Bundle()
            bundle.putInt("notesId", notesId)
            bundle.putInt("noteStatus", notesStatus)
            bundle.putString("notesSubTitle", notesSubTitle)
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
        noteViewModel.garbageNotes.observe(viewLifecycleOwner) { notes ->
            notes.sortBy { it.dateTime }
            notesAdapter.setData(notes)
        }
    }

    private fun sortByTitle() {
        noteViewModel.garbageNotes.observe(viewLifecycleOwner) { notes ->
            notes.sortBy { it.title }
            notesAdapter.setData(notes)
        }
    }

    private fun cancelNotification(id: Int, title: String, message: String, time: String) {
        val intent = Intent(context, Notification::class.java)
        intent.putExtra(TITLE_EXTRA, title)
        intent.putExtra(NOTE_ID, id)
        intent.putExtra(MESSAGE_EXTRA, "$message\n in $time")
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}