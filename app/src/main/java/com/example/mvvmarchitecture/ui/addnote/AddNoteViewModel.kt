package com.example.mvvmarchitecture.ui.addnote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.local.NoteEntity
import com.example.mvvmarchitecture.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(val noteRepository: NoteRepository) : ViewModel() {

    private val _saveData = MutableSharedFlow<Boolean>() // String → route or destination
    val saveData = _saveData.asSharedFlow() // expose read-only

    private val _noteUpdated = MutableSharedFlow<Boolean>() // String → route or destination
    val noteUpdated = _noteUpdated.asSharedFlow() // expose read-only

    fun saveNotes(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _saveData.emit(noteRepository.saveNotes(noteEntity).toInt() != -1)
            }catch (e : Exception){
                Log.i("AddNoteViewModel", "saveNotes: ${e.message}")
            }
        }
    }

    fun updateNote(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _noteUpdated.emit(noteRepository.updateNote(noteEntity).toInt() > 0)

            }catch (e : Exception){
                Log.i("AddNoteViewModel", "updateNote: ${e.message}")
            }
        }
    }
}