package com.example.mvvmarchitecture.data.repository

import com.example.mvvmarchitecture.data.datasource.NoteDataSource
import com.example.mvvmarchitecture.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(val noteDataSource: NoteDataSource){

    fun saveNotes(noteEntity: NoteEntity) : Long {
        return noteDataSource.saveNotes(noteEntity)
    }

    fun getAllNotes() : Flow<List<NoteEntity>>{
        return noteDataSource.getAllNotes()
    }

    fun deleteNote(noteId : Int) = noteDataSource.deleteNote(noteId)

    fun updateNote(noteEntity : NoteEntity) = noteDataSource.updateNote(noteEntity)
}