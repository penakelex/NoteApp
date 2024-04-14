package org.penakelex.noteapp.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import org.penakelex.noteapp.feature_note.data.data_source.NoteDao
import org.penakelex.noteapp.feature_note.domain.model.Note
import org.penakelex.noteapp.feature_note.domain.repository.NoteRepository

class NoteRepositoryImplementation(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> =
        dao.getNotes()

    override suspend fun getNoteByID(id: Int): Note? =
        dao.getNoteByID(id)

    override suspend fun insertNote(note: Note) =
        dao.insertNote(note)

    override suspend fun deleteNote(note: Note) =
        dao.deleteNote(note)
}