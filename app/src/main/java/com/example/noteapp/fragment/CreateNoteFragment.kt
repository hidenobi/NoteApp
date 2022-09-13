package com.example.noteapp.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.noteapp.R
import com.example.noteapp.database.NotesDatabase
import com.example.noteapp.databinding.FragmentCreateNoteBinding
import com.example.noteapp.entities.Notes
import com.example.noteapp.util.NoteBottomSheetFragment
import com.example.noteapp.viewmodel.NoteViewModel
import java.util.*

class CreateNoteFragment : Fragment(R.layout.fragment_create_note),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: FragmentCreateNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var selectedColor = "#171C26"
    private var noteId = -1
    private var noteStatus = 0
    private var oldNotes = Notes()
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0
    private var date: String = "Thời hạn: "
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateNoteBinding.bind(view)
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        if (noteId != -1) {
            context?.let {
                oldNotes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                binding.apply {
                    edtTitle.setText(oldNotes.title)
                    edtSubTitle.setText(oldNotes.subTitle)
                    edtNoteDesc.setText(oldNotes.noteText)
                    tvDateTime.text = oldNotes.dateTime
                    selectedColor = oldNotes.color.toString()
                    viewColor.setBackgroundColor(Color.parseColor(oldNotes.color))
                }

            }
        }
        if (noteStatus == 0) {
            binding.ivDone.visibility = View.VISIBLE
        } else {
            binding.ivDone.visibility = View.INVISIBLE
        }
        binding.apply {
            ivDone.setOnClickListener {
                if (noteId != -1) {
                    updateNote()
                } else {
                    saveNote()
                }
            }
            ivBack.setOnClickListener {
                back()
            }
            ivMore.setOnClickListener {
                openBottomSheet()
            }
            tvDateTime.setOnClickListener {
                setTime()
            }

        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter("bottom_sheet_action")
        )
    }

    private fun setTime() {
        getDateTimeCalendar()
        DatePickerDialog(
            requireContext(),
            AlertDialog.THEME_HOLO_DARK,
            this,
            year,
            month,
            day
        ).show()

    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun updateNote() {
        if (binding.edtTitle.text.isNullOrEmpty()) {
            showToast("Tiêu đề không để để trống")
        } else if (binding.edtNoteDesc.text.isNullOrEmpty()) {
            showToast("Không được để trống nội dung")
        } else if (binding.edtSubTitle.text.isNullOrEmpty()) {
            showToast("Không được để trống phân loại")
        } else {
            binding.apply {
                oldNotes.title = edtTitle.text.toString()
                oldNotes.subTitle = edtSubTitle.text.toString()
                oldNotes.dateTime = tvDateTime.text.toString()
                oldNotes.noteText = edtNoteDesc.text.toString()
                oldNotes.color = selectedColor
            }
            noteViewModel.updateNote(oldNotes)
            back()
        }
    }

    private fun openBottomSheet() {
        val noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId, noteStatus)
        noteBottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            "Note Bottom Sheet Fragment"
        )
    }

    private fun saveNote() {
        if (binding.edtTitle.text.isNullOrEmpty()) {
            showToast("Tiêu đề không để để trống")
        } else if (binding.edtNoteDesc.text.isNullOrEmpty()) {
            showToast("Không được để trống nội dung")
        } else if (binding.edtSubTitle.text.isNullOrEmpty()) {
            showToast("Không được để trống phân loại")
        } else {
            val notes = Notes()
            binding.apply {
                notes.title = edtTitle.text.toString()
                notes.subTitle = edtSubTitle.text.toString()
                notes.dateTime = tvDateTime.text.toString()
                notes.noteText = edtNoteDesc.text.toString()
                notes.color = selectedColor
            }
            noteViewModel.addNote(notes)
            back()
        }

    }

    private fun back() {
        parentFragmentManager.popBackStack()
    }

    private fun showToast(notification: String) {
        Toast.makeText(context, notification, Toast.LENGTH_SHORT).show()
    }
    // dùng callback thay thế
    // 1 callback cho action
    // 1 callback cho mã màu
    // nên cải thiện bằng R.color
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val actionColor = p1!!.getStringExtra("action")
            when (actionColor!!) {
                "#4e33ff" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "#ffd633" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "#ae3b76" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "#0aebaf" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "#ff7746" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "#202734" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "DeleteNote" -> {
                    deleteNote()
                }
                "completeNote" -> {
                    completeNote()
                }
                "removeNote" -> {
                    removeNote()
                }
                "restoreNote" -> {
                    restoreNote()
                }
                "notCompleteNote" -> {
                    restoreNote()
                }
                else -> {
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }
    }

    private fun restoreNote() {
        oldNotes.statusNote = 0
        noteViewModel.updateNote(oldNotes)
        back()
    }

    private fun removeNote() {
        noteViewModel.deleteSpecificNote(noteId)
        back()
    }


    private fun completeNote() {
        oldNotes.statusNote = 1
        noteViewModel.updateNote(oldNotes)
        back()
    }

    private fun deleteNote() {
        oldNotes.statusNote = -1
        noteViewModel.updateNote(oldNotes)
        back()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = requireArguments().getInt("notesId")
            noteStatus = requireArguments().getInt("noteStatus")
        }

    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedDay = p3
        savedMonth = p2
        savedYear = p1
        date += String.format("%02d.%02d.%04d", savedDay, savedMonth, savedYear)
        TimePickerDialog(
            requireContext(),
            AlertDialog.THEME_HOLO_DARK,
            this,
            hour,
            minute,
            true
        ).show()

    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour = p1
        savedMinute = p2
        date += String.format(" %02d:%02d", savedHour, savedMinute)
        binding.tvDateTime.text = date
    }
}