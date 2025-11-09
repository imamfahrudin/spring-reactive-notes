package com.imamfahrudin.notes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represents a note entity with id, title, and content.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("notes")
public class Note {
    @Id
    private Long id;
    private String title;
    private String content;
}