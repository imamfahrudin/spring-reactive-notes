package com.imamfahrudin.notes.repository;

import com.imamfahrudin.notes.model.Note;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface for Note entities.
 */
public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
}