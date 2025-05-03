package com.example.mvvmarchitecture.data.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmarchitecture.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun dataBaseProvide(@ApplicationContext context: Context) : NoteDatabase
    {
        return Room.databaseBuilder(
            context, NoteDatabase::class.java, "NoteDatabase.db"
        ).build()
    }


}