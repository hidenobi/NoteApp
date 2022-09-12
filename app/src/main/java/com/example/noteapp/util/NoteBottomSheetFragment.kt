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

class NoteBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomSheetBinding
    private var selectedColor = "#171C26"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBottomSheetBinding.inflate(inflater, container, false)
//        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet,container)
//        dialog?.setContentView(view)
//        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
//        val behavior = param.behavior
//        if (behavior is BottomSheetBehavior<*>) {
//            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//
//                }
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    var state = ""
//                    when (newState) {
//                        BottomSheetBehavior.STATE_DRAGGING -> {
//                            state = "DRAGGING"
//                        }
//                        BottomSheetBehavior.STATE_SETTLING -> {
//                            state = "SETTLING"
//                        }
//                        BottomSheetBehavior.STATE_EXPANDED -> {
//                            state = "EXPANDED"
//                        }
//                        BottomSheetBehavior.STATE_COLLAPSED -> {
//                            state = "COLLAPSED"
//                        }
//
//                        BottomSheetBehavior.STATE_HIDDEN -> {
//                            state = "HIDDEN"
//                            dismiss()
//                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                        }
//                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//                            state = "HALF_EXPANDED"
//                        }
//                    }
//                }
//            })
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (noteId != -1) {
                when (noteStatus) {
                    0 -> {
                        tvRemoveNote.visibility = View.GONE
                        tvDeleteNote.visibility = View.VISIBLE
                        tvCompleteNote.visibility = View.VISIBLE
                        tvNotCompleteNote.visibility = View.GONE
                        tvRestoreNote.visibility = View.GONE
                    }
                    1 -> {
                        tvRemoveNote.visibility = View.GONE
                        tvDeleteNote.visibility = View.VISIBLE
                        tvCompleteNote.visibility = View.GONE
                        tvNotCompleteNote.visibility = View.VISIBLE
                        tvRestoreNote.visibility = View.GONE
                    }
                    -1 -> {
                        tvRemoveNote.visibility = View.VISIBLE
                        tvDeleteNote.visibility = View.GONE
                        tvCompleteNote.visibility = View.GONE
                        tvNotCompleteNote.visibility = View.GONE
                        tvRestoreNote.visibility = View.VISIBLE
                    }
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
            ivDone.setOnClickListener {
                dismiss()
            }
            flNoteColorBlue.setOnClickListener {
                ivNoteColorBlue.setImageResource(R.drawable.ic_done)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#4e33ff"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
            flNoteColorYellow.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(R.drawable.ic_done)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#ffd633"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }

            flNoteColorPurple.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(R.drawable.ic_done)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#ae3b76"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorGreen.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorGreen.setImageResource(R.drawable.ic_done)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#0aebaf"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorOrange.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(R.drawable.ic_done)
                selectedColor = "#ff7746"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorBlack.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
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
        fun newInstance(id: Int, status: Int): NoteBottomSheetFragment {
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            noteStatus = status
            return fragment
        }
    }

}