package com.imamfahrudin.notes.controller;

import com.imamfahrudin.notes.model.Note;
import com.imamfahrudin.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for note operations.
 */
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    /**
     * Retrieves all notes.
     *
     * @return a Flux of all notes
     */
    @GetMapping
    public Flux<Note> getAllNotes() {
        return noteService.findAll();
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id the ID of the note
     * @return a Mono of the note if found
     */
    @GetMapping("/{id}")
    public Mono<Note> getNoteById(@PathVariable @NonNull Long id) {
        return noteService.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")));
    }

    /**
     * Creates a new note.
     *
     * @param note the note to create
     * @return a Mono of the created note
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Note> createNote(@RequestBody @NonNull Note note) {
        note.setId(null); // Ensure it's a new note (INSERT instead of UPDATE)
        return noteService.save(note);
    }

    /**
     * Updates an existing note.
     *
     * @param id the ID of the note to update
     * @param note the updated note data
     * @return a Mono of the updated note
     */
    @PutMapping("/{id}")
    public Mono<Note> updateNote(@PathVariable @NonNull Long id, @RequestBody @NonNull Note note) {
        note.setId(id);
        return noteService.save(note);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id the ID of the note to delete
     * @return a Mono of Void
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteNote(@PathVariable @NonNull Long id) {
        return noteService.deleteById(id);
    }
}