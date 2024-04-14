package org.penakelex.noteapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.penakelex.noteapp.feature_note.data.data_source.NoteDatabase
import org.penakelex.noteapp.feature_note.data.repository.NoteRepositoryImplementation
import org.penakelex.noteapp.feature_note.domain.repository.NoteRepository
import org.penakelex.noteapp.feature_note.domain.use_case.AddNoteUseCase
import org.penakelex.noteapp.feature_note.domain.use_case.DeleteNoteUseCase
import org.penakelex.noteapp.feature_note.domain.use_case.GetNoteUseCase
import org.penakelex.noteapp.feature_note.domain.use_case.GetNotesUseCase
import org.penakelex.noteapp.feature_note.domain.use_case.NoteUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(
        application: Application
    ): NoteDatabase = Room.databaseBuilder(
        application,
        NoteDatabase::class.java,
        NoteDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: NoteDatabase
    ): NoteRepository = NoteRepositoryImplementation(
        dao = database.noteDao
    )

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepository
    ): NoteUseCases = NoteUseCases(
        getNotes = GetNotesUseCase(repository),
        deleteNote = DeleteNoteUseCase(repository),
        addNote = AddNoteUseCase(repository),
        getNote = GetNoteUseCase(repository)
    )
}