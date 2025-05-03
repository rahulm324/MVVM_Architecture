package com.example.mvvmarchitecture.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity) : Long     // return id if successful else if failed -1

    @Query("Select * from tbl_notes")
    fun getNotes() : Flow<List<NoteEntity>>

    @Query("Delete FROM tbl_notes WHERE id = :id")
    fun deleteNote( id: Int) : Int

    //@Query("UPDATE tbl_notes SET noteTitle = :noteTitle, noteBody = :noteBody WHERE id = :id")    //Optional
    @Update
    fun updateNote(noteEntity: NoteEntity) : Int

}