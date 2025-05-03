package com.example.mvvmarchitecture.ui.addnote

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.local.NoteEntity
import com.example.mvvmarchitecture.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    lateinit var  etTitle : EditText
    lateinit var  etNote : EditText
    lateinit var addNoteBinding : FragmentAddNoteBinding
    private val viewModel: AddNoteViewModel by viewModels()

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        etTitle = addNoteBinding.etTitle
        etNote = addNoteBinding.etNote
        val btnSave = addNoteBinding.btnSave

        val bundle = arguments
        if (bundle != null && bundle.containsKey("noteEntity")) {
            var noteEntity = bundle.getParcelable<NoteEntity>("noteEntity")
            etTitle.setText( noteEntity?.title.toString())
            etNote.setText(noteEntity?.noteBody.toString())
            btnSave.text = getString(R.string.update)
        }


        btnSave.setOnClickListener {
            if (validateData()) {

                if (bundle != null && bundle.containsKey("noteEntity")) {
                    var noteEntity = bundle.getParcelable<NoteEntity>("noteEntity")
                    noteEntity?.title = addNoteBinding.etTitle.text.toString()
                    noteEntity?.noteBody = addNoteBinding.etNote.text.toString()
                    if (noteEntity?.id != null) {
                        val noteEntity2 =
                            NoteEntity(
                                id = noteEntity!!.id,
                                title = etTitle.text.toString(),
                                noteBody = etNote.text.toString()
                            )
                        viewModel.updateNote(noteEntity2)
                        Log.i("TAG", "onCreateView: note update called")
                    }
                }else{
                    Log.i("TAG", "onCreateView: note saved called ")
                    viewModel.saveNotes(NoteEntity(title = etTitle.text.toString(), noteBody = etNote.text.toString()))
                }
            }
        }
        return addNoteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.saveData.collect {
                if (it){
                    Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Record not saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.noteUpdated.collect {
                if (it){
                    Toast.makeText(requireContext(), "Updated successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Not updated!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun validateData(): Boolean {
        if (etTitle.text.toString().isNotEmpty()) {
            if (etNote.text.toString().isNotEmpty()) {
                return true
            } else {
                Toast.makeText(context, "Enter Notes", Toast.LENGTH_SHORT).show()
                return false
            }
        } else {
            Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}