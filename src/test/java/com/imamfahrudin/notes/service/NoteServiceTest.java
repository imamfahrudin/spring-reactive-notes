package com.imamfahrudin.notes.service;

import com.imamfahrudin.notes.model.Note;
import com.imamfahrudin.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for NoteService using Mockito and StepVerifier.
 */
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note testNote;

    @BeforeEach
    void setUp() {
        testNote = new Note();
        testNote.setId(1L);
        testNote.setTitle("Test Title");
        testNote.setContent("Test Content");
    }

    /**
     * Test finding all notes.
     */
    @Test
    void shouldFindAllNotes() {
        when(noteRepository.findAll()).thenReturn(Flux.just(testNote));

        Flux<Note> result = noteService.findAll();

        StepVerifier.create(result)
                .expectNext(testNote)
                .verifyComplete();
    }

    /**
     * Test finding a note by ID when it exists.
     */
    @Test
    void shouldFindNoteByIdWhenExists() {
        when(noteRepository.findById(1L)).thenReturn(Mono.just(testNote));

        Mono<Note> result = noteService.findById(1L);

        StepVerifier.create(result)
                .expectNext(testNote)
                .verifyComplete();
    }

    /**
     * Test finding a note by ID when it does not exist.
     */
    @Test
    void shouldFindNoteByIdWhenNotExists() {
        when(noteRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Note> result = noteService.findById(1L);

        StepVerifier.create(result)
                .verifyComplete();
    }

    /**
     * Test saving a note.
     */
    @Test
    @SuppressWarnings("null")
    void shouldSaveNote() {
        when(noteRepository.save(any(Note.class))).thenReturn(Mono.just(testNote));

        Mono<Note> result = noteService.save(testNote);

        StepVerifier.create(result)
                .expectNext(testNote)
                .verifyComplete();
    }

    /**
     * Test deleting a note by ID.
     */
    @Test
    void shouldDeleteNoteById() {
        when(noteRepository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = noteService.deleteById(1L);

        StepVerifier.create(result)
                .verifyComplete();
    }
}