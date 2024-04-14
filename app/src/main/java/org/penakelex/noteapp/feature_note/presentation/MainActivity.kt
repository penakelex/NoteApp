package org.penakelex.noteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import org.penakelex.noteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import org.penakelex.noteapp.feature_note.presentation.notes.NotesScreen
import org.penakelex.noteapp.feature_note.presentation.util.Screen
import org.penakelex.noteapp.ui.theme.NoteAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(route = Screen.NoteScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = "${
                                Screen.AddEditNoteScreen.route
                            }?noteID={noteID}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteID"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.LongType
                                    defaultValue = Color.Transparent.value
                                }
                            )
                        ) {
                            val color = it.arguments?.getLong("noteColor")?.toULong()
                                ?: Color.Transparent.value
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = Color(color)
                            )
                        }
                    }
                }
            }
        }
    }
}