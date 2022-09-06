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
        binding = FragmentNotesBottomSheetBinding.inflate(inflater,container,false)
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
        if(noteId!=-1){
            binding.tvDeleteNote.visibility = View.VISIBLE
        }
        else{
            binding.tvDeleteNote.visibility = View.GONE
        }
        setListener()
    }
    private fun setListener(){
        binding.apply {
            ivDone.setOnClickListener{
                dismiss()
            }
            flNoteColorBlue.setOnClickListener {
                ivNoteColorBlue.setImageResource(R.drawable.ic_done)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorWhite.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#4e33ff"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
            flNoteColorYellow.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(R.drawable.ic_done)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorWhite.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#ffd633"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorWhite.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorWhite.setImageResource(R.drawable.ic_done)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#ffffff"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorPurple.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(R.drawable.ic_done)
                ivNoteColorWhite.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#ae3b76"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorGreen.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorWhite.setImageResource(0)
                ivNoteColorGreen.setImageResource(R.drawable.ic_done)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#0aebaf"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorOrange.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorWhite.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(0)
                ivNoteColorOrange.setImageResource(R.drawable.ic_done)
                selectedColor = "#ff7746"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            flNoteColorBlack.setOnClickListener {
                ivNoteColorBlue.setImageResource(0)
                ivNoteColorYellow.setImageResource(0)
                ivNoteColorPurple.setImageResource(0)
                ivNoteColorWhite.setImageResource(0)
                ivNoteColorGreen.setImageResource(0)
                ivNoteColorBlack.setImageResource(R.drawable.ic_done)
                ivNoteColorOrange.setImageResource(0)
                selectedColor = "#202734"
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action",selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            }
            tvDeleteNote.setOnClickListener {
                val intent = Intent("bottom_sheet_action")
                intent.putExtra("action","DeleteNote")
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                dismiss()
            }
        }
    }
    companion object {
        var noteId = -1
        fun newInstance(id:Int): NoteBottomSheetFragment{
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            return fragment
        }
    }

}