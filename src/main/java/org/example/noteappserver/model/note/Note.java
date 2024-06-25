package org.example.noteappserver.model.note;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class Note {
    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Note(Long id, String title, String content, Long memberId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
