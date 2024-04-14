package org.penakelex.noteapp.feature_note.domain.use_case

import org.penakelex.noteapp.feature_note.domain.model.Note
import org.penakelex.noteapp.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? =
        repository.getNoteByID(id)
}