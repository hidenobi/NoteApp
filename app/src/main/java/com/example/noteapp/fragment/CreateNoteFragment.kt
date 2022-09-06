package com.example.noteapp.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.noteapp.viewmodel.NoteViewModel
import com.example.noteapp.R
import com.example.noteapp.database.NotesDatabase
import com.example.noteapp.databinding.FragmentCreateNoteBinding
import com.example.noteapp.entities.Notes
import com.example.noteapp.util.NoteBottomSheetFragment

class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {
    private lateinit var binding: FragmentCreateNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var selectedColor = "#171C26"
    private var noteId = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateNoteBinding.bind(view)
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
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
        }
        if (noteId != -1) {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                binding.apply {
                    edtTitle.setText(notes.title)
                    edtSubTitle.setText(notes.subTitle)
                    edtNoteDesc.setText(notes.noteText)
                    tvDateTime.text = notes.dateTime
                    selectedColor = notes.color.toString()
                    viewColor.setBackgroundColor(Color.parseColor(notes.color))
                }

            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter("bottom_sheet_action")
        )
    }

    private fun updateNote() {
        if (binding.edtTitle.text.isNullOrEmpty()) {
            showToast("Tiêu đề không để để trống")
        } else if (binding.edtNoteDesc.text.isNullOrEmpty()) {
            showToast("Không được để trống nội dung")
        } else if (binding.edtSubTitle.text.isNullOrEmpty()) {
            showToast("Không được để trống phân loại")
        } else {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                binding.apply {
                    notes.title = edtTitle.text.toString()
                    notes.subTitle = edtSubTitle.text.toString()
                    notes.dateTime = tvDateTime.text.toString()
                    notes.noteText = edtNoteDesc.text.toString()
                    notes.color = selectedColor
                }
                noteViewModel.updateNote(notes)
                back()
            }
        }
    }

    private fun openBottomSheet() {
        val noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId)
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
                "#ffffff" -> {
                    selectedColor = actionColor
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "DeleteNote" -> {
                    deleteNote()
                }
                else -> {
                    binding.viewColor.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }
    }

    private fun deleteNote() {
        noteViewModel.deleteSpecificNote(noteId)
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
        }
    }
}