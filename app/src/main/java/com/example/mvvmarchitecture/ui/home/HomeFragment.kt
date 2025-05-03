package com.example.mvvmarchitecture.ui.home


import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.local.NoteEntity
import com.example.mvvmarchitecture.databinding.FragmentHomeBinding
import com.example.mvvmarchitecture.ui.addnote.AddNoteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var rvNoteList: RecyclerView

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        rvNoteList = fragmentHomeBinding.rvNoteList
        rvNoteList.layoutManager = LinearLayoutManager(activity)



        fragmentHomeBinding.fbAddNotes.setOnClickListener {
            if (activity?.supportFragmentManager != null) {

                var transaction = activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, AddNoteFragment.newInstance())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }

        }
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.noteList.collect {
                    rvNoteList.adapter = NotesAdapter(it,
                        deleteNote = { noteId -> deleteNote(noteId)},
                        updateNote = { noteEntity -> updateNote(noteEntity)
                        }
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED ) {
                viewModel.isDeleted.collect {
                    if (it){
                        Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Not Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateNote(entity: NoteEntity) {
        val bundle = Bundle().apply {
            putParcelable("noteEntity", entity )
        }
        val addNoteFragment = AddNoteFragment.newInstance().apply { arguments = bundle }

        var transaction = activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, addNoteFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }


    private fun deleteNote(noteId: Int) {
        viewModel.deleteNote(noteId)
    }

    override fun onStart() {
        super.onStart()
        //fetching list of notes
        viewModel.getAllNotes()
    }
}