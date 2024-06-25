package org.example.noteappserver.domain.note.dto;

import lombok.Getter;
import org.example.noteappserver.model.note.Note;

import java.time.LocalDateTime;

@Getter
public class NoteResponseDTO {
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public NoteResponseDTO(Note note) {
        this.title = note.getTitle();
        this.content = note.getContent();
        this.updatedAt = note.getUpdatedAt();
    }
}
