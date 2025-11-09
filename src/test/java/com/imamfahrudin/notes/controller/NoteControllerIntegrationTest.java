package com.imamfahrudin.notes.controller;

import com.imamfahrudin.notes.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for NoteController using WebTestClient, JUnit 5, and Hamcrest.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("null")
public class NoteControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private Note testNote;

    @BeforeEach
    void setUp() {
        testNote = new Note();
        testNote.setTitle("Test Title");
        testNote.setContent("Test Content");
    }

    /**
     * Test creating a new note.
     */
    @Test
    void shouldCreateNote() {
        webTestClient.post()
                .uri("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testNote)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Note.class)
                .value(note -> {
                    assertThat(note.getId(), notNullValue());
                    assertThat(note.getTitle(), equalTo("Test Title"));
                    assertThat(note.getContent(), equalTo("Test Content"));
                });
    }

    /**
     * Test retrieving all notes.
     */
    @Test
    void shouldGetAllNotes() {
        // First create a note
        webTestClient.post()
                .uri("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testNote)
                .exchange()
                .expectStatus().isCreated();

        // Then get all notes
        webTestClient.get()
                .uri("/notes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Note.class)
                .value(notes -> {
                    assertThat(notes, not(empty()));
                    assertThat(notes, hasSize(greaterThanOrEqualTo(1)));
                });
    }

    /**
     * Test retrieving a note by ID.
     */
    @Test
    void shouldGetNoteById() {
        // First create a note
        Note createdNote = webTestClient.post()
                .uri("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testNote)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Note.class)
                .getResponseBody()
                .blockFirst();

        // Then get the note by ID
        webTestClient.get()
                .uri("/notes/{id}", createdNote.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Note.class)
                .value(note -> {
                    assertThat(note.getId(), equalTo(createdNote.getId()));
                    assertThat(note.getTitle(), equalTo("Test Title"));
                    assertThat(note.getContent(), equalTo("Test Content"));
                });
    }

    /**
     * Test updating a note.
     */
    @Test
    void shouldUpdateNote() {
        // First create a note
        Note createdNote = webTestClient.post()
                .uri("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testNote)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Note.class)
                .getResponseBody()
                .blockFirst();

        // Update the note
        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");

        webTestClient.put()
                .uri("/notes/{id}", createdNote.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedNote)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Note.class)
                .value(note -> {
                    assertThat(note.getId(), equalTo(createdNote.getId()));
                    assertThat(note.getTitle(), equalTo("Updated Title"));
                    assertThat(note.getContent(), equalTo("Updated Content"));
                });
    }

    /**
     * Test deleting a note.
     */
    @Test
    void shouldDeleteNote() {
        // First create a note
        Note createdNote = webTestClient.post()
                .uri("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testNote)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Note.class)
                .getResponseBody()
                .blockFirst();

        // Delete the note
        webTestClient.delete()
                .uri("/notes/{id}", createdNote.getId())
                .exchange()
                .expectStatus().isNoContent();

        // Verify the note is deleted
        webTestClient.get()
                .uri("/notes/{id}", createdNote.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test retrieving a non-existent note.
     */
    @Test
    void shouldReturnNotFoundForNonExistentNote() {
        webTestClient.get()
                .uri("/notes/{id}", 999L)
                .exchange()
                .expectStatus().isNotFound();
    }
}