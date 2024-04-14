package org.penakelex.noteapp.feature_note.domain.use_case

import org.penakelex.noteapp.feature_note.domain.model.InvalidNoteException
import org.penakelex.noteapp.feature_note.domain.model.Note
import org.penakelex.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) throw InvalidNoteException(
            message = "The title of the note can`t be empty."
        )
        repository.insertNote(note)
    }
}