package com.imamfahrudin.notes.repository;

import com.imamfahrudin.notes.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for NoteRepository using H2 in-memory database and StepVerifier.
 */
@SpringBootTest
@SuppressWarnings("null")
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    private Note testNote;

    @BeforeEach
    void setUp() {
        testNote = new Note();
        testNote.setTitle("Test Title");
        testNote.setContent("Test Content");

        // Clean up before each test
        noteRepository.deleteAll().block();
    }

    /**
     * Test saving a note.
     */
    @Test
    void shouldSaveNote() {
        Mono<Note> savedNote = noteRepository.save(testNote);

        StepVerifier.create(savedNote)
                .assertNext(note -> {
                    assertThat(note.getId()).isNotNull();
                    assertThat(note.getTitle()).isEqualTo("Test Title");
                    assertThat(note.getContent()).isEqualTo("Test Content");
                })
                .verifyComplete();
    }

    /**
     * Test finding all notes.
     */
    @Test
    void shouldFindAllNotes() {
        noteRepository.save(testNote).block();

        Flux<Note> notes = noteRepository.findAll();

        StepVerifier.create(notes)
                .assertNext(note -> {
                    assertThat(note.getId()).isNotNull();
                    assertThat(note.getTitle()).isEqualTo("Test Title");
                    assertThat(note.getContent()).isEqualTo("Test Content");
                })
                .verifyComplete();
    }

    /**
     * Test finding a note by ID.
     */
    @Test
    void shouldFindNoteById() {
        Note savedNote = noteRepository.save(testNote).block();

        Mono<Note> foundNote = noteRepository.findById(savedNote.getId());

        StepVerifier.create(foundNote)
                .assertNext(note -> {
                    assertThat(note.getId()).isEqualTo(savedNote.getId());
                    assertThat(note.getTitle()).isEqualTo("Test Title");
                    assertThat(note.getContent()).isEqualTo("Test Content");
                })
                .verifyComplete();
    }

    /**
     * Test deleting a note by ID.
     */
    @Test
    void shouldDeleteNoteById() {
        Note savedNote = noteRepository.save(testNote).block();

        Mono<Void> deleteResult = noteRepository.deleteById(savedNote.getId());

        StepVerifier.create(deleteResult)
                .verifyComplete();

        // Verify the note was deleted
        Mono<Note> foundNote = noteRepository.findById(savedNote.getId());
        StepVerifier.create(foundNote)
                .verifyComplete(); // Should be empty
    }
}