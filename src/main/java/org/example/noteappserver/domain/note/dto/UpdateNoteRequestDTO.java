package org.example.noteappserver.domain.note.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteRequestDTO {
    private String title;
    private String content;
}