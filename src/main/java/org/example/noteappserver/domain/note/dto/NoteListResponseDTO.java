package org.example.noteappserver.domain.note.dto;

import lombok.Getter;
import org.example.noteappserver.model.note.Note;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NoteListResponseDTO {

    private List<NoteDTO> noteList;

    public NoteListResponseDTO(List<Note> noteList){
        this.noteList = noteList.stream().map(note -> new NoteDTO(note)).toList();
    }

    @Getter
    public static class NoteDTO {
        private Long id;
        private String title;
        private LocalDateTime updatedAt;

        public NoteDTO(Note note) {
            this.id = note.getId();
            this.title = note.getTitle();
            this.updatedAt = note.getUpdatedAt();
        }
    }
}
