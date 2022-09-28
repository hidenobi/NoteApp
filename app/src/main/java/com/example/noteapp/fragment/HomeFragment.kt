package com.example.noteapp.fragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.noteapp.broadcast.*
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment(R.layout.fragment_home), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        addFragment()
        initAdapter()
        initViewModel()
        setListener()
    }

    private fun setListener() {
        binding.apply {
            ivMore.setOnClickListener {
                PopupMenu(requireContext(), it).apply {
                    setOnMenuItemClickListener(this@HomeFragment)
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
        noteViewModel.searchByTitle(tmp, 0).observe(viewLifecycleOwner) { notes ->
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
        noteViewModel.homeNotes.observe(viewLifecycleOwner) {
            for (note in it) {
                val sdf = SimpleDateFormat(
                    "dd.MM.yyyy HH:mm",
                    Locale.getDefault()
                ).parse(note.dateTime!!)!!.time
                val currentTime = System.currentTimeMillis()
                if (sdf > currentTime) {
                    Log.i("TAG", "initViewModel: true")
                    scheduleNotification(note.id!!, note.title!!, note.noteText!!, note.dateTime!!)
                }
                if (sdf < currentTime) {
                    note.statusNote = -2
                    note.title += "(Quá hạn)"
                    noteViewModel.updateNote(note)
                }
            }
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

    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc
        val notificationManager =
            activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun scheduleNotification(id: Int, title: String, message: String, time: String) {
        val intent = Intent(context, Notification::class.java)
        intent.putExtra(TITLE_EXTRA, title)
        intent.putExtra(NOTE_ID, id)
        intent.putExtra(MESSAGE_EXTRA, "$message\n lúc $time")
        val setTime =
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).parse(time)!!.time
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setTime, pendingIntent)
    }

}