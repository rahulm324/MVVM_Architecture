package com.example.mvvmarchitecture.data.datasource

import com.example.mvvmarchitecture.data.local.NoteDatabase
import com.example.mvvmarchitecture.data.local.NoteEntity
import javax.inject.Inject

class NoteDataSource @Inject constructor(val noteDatabase: NoteDatabase) {
    fun saveNotes(noteEntity: NoteEntity) = noteDatabase.getNoteDao().insertNote(noteEntity)

    fun getAllNotes() = noteDatabase.getNoteDao().getNotes()

    fun deleteNote(noteId : Int) = noteDatabase.getNoteDao().deleteNote(noteId)

    fun updateNote(noteEntity: NoteEntity) = noteDatabase.getNoteDao().updateNote(noteEntity)
}