package com.example.mvvmarchitecture.ui.home

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.data.local.NoteEntity
import com.example.mvvmarchitecture.databinding.LayoutItemNoteListBinding

class NotesAdapter(val list : List<NoteEntity>,
                   val deleteNote :(Int) -> Unit,
                   val updateNote :(NoteEntity) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    lateinit var binding: LayoutItemNoteListBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
       binding = LayoutItemNoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        with(holder){
            with(list[position]){
                binding.tvTitle.text = title
                binding.tvNote.text = noteBody

                binding.btnDelete.setOnClickListener {
                    deleteNote(list[position].id)
                }
                binding.btnEdit.setOnClickListener {
                    updateNote(list[position])
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NoteViewHolder(binding: LayoutItemNoteListBinding) : RecyclerView.ViewHolder(binding.root)
}