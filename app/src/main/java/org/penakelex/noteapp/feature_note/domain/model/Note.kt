package org.penakelex.noteapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Note(
    @PrimaryKey val id: Int?,
    val title: String,
    val content: String,
    val timestamp: Instant,
    val color: Color
) {
    companion object {
        val noteColors = listOf<Color>(
            //TODO: Сделать цвета
        )
    }
}
