package com.smitcoderx.scratch.data.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM Note WHERE id IN (:noteIds)")
    suspend fun deleteNotes(noteIds: Set<Int>)

    @Query("SELECT * FROM Note")
    fun fetchNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note where id = :noteId")
    suspend fun fetchNote(noteId: Int?): Note?

    @Query("SELECT * FROM Note WHERE categoryId = :categoryId")
    fun fetchNotesByCategory(categoryId: Int): Flow<List<Note>>
}
