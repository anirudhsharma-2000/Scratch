package com.smitcoderx.scratch.data.note

class NoteRepository(private val noteDao: NoteDao) {
    suspend fun addNote(note: Note) = noteDao.addNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun fetchNotes() = noteDao.fetchNotes()

    suspend fun fetchNote(noteId: Int) = noteDao.fetchNote(noteId)

    suspend fun fetchNotesByCategory(categoryId: Int) =
        noteDao.fetchNotesByCategory(categoryId)
}
