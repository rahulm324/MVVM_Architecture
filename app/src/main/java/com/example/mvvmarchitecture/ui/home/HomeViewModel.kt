package com.example.mvvmarchitecture.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.local.NoteEntity
import com.example.mvvmarchitecture.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    var noteList = MutableStateFlow<List<NoteEntity>>(value = emptyList())

    private var _isDeleted = MutableSharedFlow<Boolean>()
    val isDeleted : SharedFlow<Boolean> get() = _isDeleted

    fun getAllNotes() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllNotes().collect { notes ->
                    noteList.value = notes
                }
            }
        }catch (e : Exception){
            Log.i("HomeViewModel", "getAllNotes: ${e.message}")
        }
    }


    fun deleteNote(noteId : Int){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val deletedRows = repository.deleteNote(noteId)
                _isDeleted.emit(deletedRows > 0)
            }
        }catch (e : Exception){
            Log.i("HomeViewModel", "deleteNote: ${e.message}")
        }
    }
}