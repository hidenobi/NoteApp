package com.example.noteapp.util

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNotesBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val PRIORITY = "Ưu tiên"
const val SUPER_PRIORITY = "Cực kỳ khẩn cấp"
const val EMERGENCY = "Khẩn cấp"

class NoteBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomSheetBinding
    private var selectedColor = "#171C26"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (noteId != -1) {
                when (noteStatus) {
                    0 -> {
                        linearLayoutColor.visibility = View.VISIBLE
                        tvRemoveNote.visibility = View.GONE
                        tvDeleteNote.visibility = View.VISIBLE
                        tvCompleteNote.visibility = View.VISIBLE
                        tvNotCompleteNote.visibility = View.GONE
                        tvRestoreNote.visibility = View.GONE
                    }
                    1 -> {
                        linearLayoutColor.visibility = View.GONE
                        tvRemoveNote.visibility = View.GONE
                        tvDeleteNote.visibility = View.VISIBLE
                        tvCompleteNote.visibility = View.GONE
                        tvNotCompleteNote.visibility = View.VISIBLE
                        tvRestoreNote.visibility = View.GONE
                    }
                    -1 -> {
                        linearLayoutColor.visibility = View.GONE
                        tvRemoveNote.visibility = View.VISIBLE
                        tvDeleteNote.visibility = View.GONE
                        tvCompleteNote.visibility = View.GONE
                        tvNotCompleteNote.visibility = View.GONE
                        tvRestoreNote.visibility = View.VISIBLE
                    }
                    else -> {
                        linearLayoutColor.visibility = View.GONE
                        tvRemoveNote.visibility = View.VISIBLE
                        tvDeleteNote.visibility = View.GONE
                        tvCompleteNote.visibility = View.GONE
                        tvNotCompleteNote.visibility = View.GONE
                        tvRestoreNote.visibility = View.GONE
                    }
                }
                when (notesSubTitle) {
                    SUPER_PRIORITY -> ivNoteColorOrange.setImageResource(R.drawable.ic_done)
                    EMERGENCY -> ivNoteColorYellow.setImageResource(R.drawable.ic_done)
                    PRIORITY -> ivNoteColorGreen.setImageResource(R.drawable.ic_done)
                    else -> ivNoteColorBlack.setImageResource(R.drawable.ic_done)

                }

            } else {
                tvNotCompleteNote.visibility = View.GONE
                tvRemoveNote.visibility = View.GONE
                tvDeleteNote.visibility = View.GONE
                tvCompleteNote.visibility = View.GONE
                tvRestoreNote.visibility = View.GONE
            }
        }
        setListener()
    }

    private fun setListener() {
        binding.apply {
            ivClose.setOnClickListener {
                dismiss()
            }

            flNoteColorYellow.setOnClickListener {
                ivNoteColorYellow.setImageResource(R.drawable.ic_done)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#ffd633"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }

            flNoteColorGreen.setOnClickListener {
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorGreen.setImageResource(R.drawable.ic_done)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#0aebaf"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorOrange.setOnClickListener {
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(R.drawable.ic_done)
                selectedColor = "#ff7746"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorBlack.setOnClickListener {
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(R.drawable.ic_done)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#202734"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }

            tvDeleteNote.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "DeleteNote")
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                dismiss()
            }

            tvCompleteNote.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "completeNote")
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                dismiss()
            }

            tvRemoveNote.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "removeNote")
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                dismiss()
            }

            tvRestoreNote.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "restoreNote")
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                dismiss()
            }

            tvNotCompleteNote.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", "notCompleteNote")
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                dismiss()
            }
        }
    }

    companion object {
        var noteId = -1
        var noteStatus = 0
        var notesSubTitle = ""
        fun newInstance(id: Int, status: Int, subTitle: String): NoteBottomSheetFragment {
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            noteStatus = status
            notesSubTitle = subTitle
            return fragment
        }
    }

}