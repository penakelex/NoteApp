package org.penakelex.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.penakelex.noteapp.feature_note.domain.model.InvalidNoteException
import org.penakelex.noteapp.feature_note.domain.model.Note
import org.penakelex.noteapp.feature_note.domain.use_case.NoteUseCases
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>("noteID")?.let { noteID ->
            if (noteID != -1) viewModelScope.launch(Dispatchers.Default) {
                noteUseCases.getNote(noteID)?.also { note ->
                    currentNoteID = note.id
                    _noteTitle.value = noteTitle.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _noteContent.value = noteContent.value.copy(
                        text = note.content,
                        isHintVisible = note.content.isEmpty()
                    )
                    _noteColor.value = note.color
                }
            }
        }
    }

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title"
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content"
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.first())
    val noteColor: State<Color> = _noteColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteID: Int? = null

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch(Dispatchers.Default) {
                    try {
                        noteUseCases.addNote(
                            Note(
                                id = currentNoteID,
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = Instant.now(),
                                color = noteColor.value
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (exception: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                message = exception.message
                                    ?: "Couldn`t save note."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        data object SaveNote : UIEvent()
    }
}