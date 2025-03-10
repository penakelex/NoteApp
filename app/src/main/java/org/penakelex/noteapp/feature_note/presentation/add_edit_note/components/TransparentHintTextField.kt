package org.penakelex.noteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) = Box(
    modifier = modifier
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged(onFocusChange),
        value = text,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = textStyle
    )
    if (isHintVisible) Text(
        text = hint,
        style = textStyle,
        color = Color.DarkGray
    )
}