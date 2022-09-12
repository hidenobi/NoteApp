package com.example.noteapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemBinding
import com.example.noteapp.entities.Notes

class NotesAdapter(private val callBack: (Notes) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var listener: OnItemClickListener? = null
    private var arrList = mutableListOf<Notes>()

    inner class NotesViewHolder(
        private val binding: ItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Notes) {
            binding.apply {
                tvTitle.text = item.title
                tvDateTime.text = item.dateTime
                tvDesc.text = item.noteText
                cardViewItem.setOnClickListener {
                    listener!!.onClicked(item.id!!, item.statusNote)
                }
            }
            if (item.color != null) {
                binding.cardViewItem.setCardBackgroundColor(Color.parseColor(item.color))
            } else {
                binding.cardViewItem.setCardBackgroundColor(Color.parseColor("#171C26"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bindData(arrList[position])
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(notes: MutableList<Notes>) {
        this.arrList = notes
        notifyDataSetChanged()
    }

    //    private fun sendData(notes: Notes) {
//        callBack(notes)
//    }
    interface OnItemClickListener {
        fun onClicked(notesId: Int, notesStatus: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}