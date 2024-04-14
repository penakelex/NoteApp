package org.penakelex.noteapp.feature_note.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.penakelex.noteapp.feature_note.domain.model.Note
import org.penakelex.noteapp.feature_note.domain.repository.NoteRepository
import org.penakelex.noteapp.feature_note.domain.util.NoteOrder
import org.penakelex.noteapp.feature_note.domain.util.OrderType

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> = repository.getNotes().map { notes ->
        when (noteOrder.orderType) {
            is OrderType.Ascending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedBy {
                        it.title.lowercase()
                    }

                    is NoteOrder.Date -> notes.sortedBy {
                        it.timestamp
                    }

                    is NoteOrder.Color -> notes.sortedBy {
                        it.color.value
                    }
                }
            }

            is OrderType.Descending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedByDescending {
                        it.title.lowercase()
                    }

                    is NoteOrder.Date -> notes.sortedByDescending {
                        it.timestamp
                    }

                    is NoteOrder.Color -> notes.sortedByDescending {
                        it.color.value
                    }
                }
            }
        }
    }
}