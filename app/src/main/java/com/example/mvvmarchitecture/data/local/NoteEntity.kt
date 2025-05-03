package com.example.mvvmarchitecture.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_notes")
class NoteEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "noteTitle") var title : String,
    @ColumnInfo(name = "noteBody") var noteBody : String
): Parcelable