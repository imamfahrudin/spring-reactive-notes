package com.imamfahrudin.notes.service;

import com.imamfahrudin.notes.model.Note;
import com.imamfahrudin.notes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service class for managing notes.
 */
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * Finds all notes.
     *
     * @return a Flux of all notes
     */
    public Flux<Note> findAll() {
        return noteRepository.findAll();
    }

    /**
     * Finds a note by its ID.
     *
     * @param id the ID of the note
     * @return a Mono of the note if found
     */
    public Mono<Note> findById(@NonNull Long id) {
        return noteRepository.findById(id);
    }

    /**
     * Saves a note.
     *
     * @param note the note to save
     * @return a Mono of the saved note
     */
    public Mono<Note> save(@NonNull Note note) {
        return noteRepository.save(note);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id the ID of the note to delete
     * @return a Mono of Void
     */
    public Mono<Void> deleteById(@NonNull Long id) {
        return noteRepository.deleteById(id);
    }
}