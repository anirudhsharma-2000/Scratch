package com.smitcoderx.scratch.data.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note")
    suspend fun fetchNotes(): List<Note>

    @Query("SELECT * FROM Note where id = :noteId")
    suspend fun fetchNote(noteId: Int): Note?

    @Query("SELECT * FROM Note WHERE categoryId = :categoryId")
    suspend fun fetchNotesByCategory(categoryId: Int): List<Note>
}
