package org.example.noteappserver.domain.note.service;

import lombok.RequiredArgsConstructor;
import org.example.noteappserver.domain.note.dto.CreateNoteRequestDTO;
import org.example.noteappserver.domain.note.dto.NoteListResponseDTO;
import org.example.noteappserver.domain.note.dto.NoteResponseDTO;
import org.example.noteappserver.domain.note.dto.UpdateNoteRequestDTO;
import org.example.noteappserver.global.exception.Exception400;
import org.example.noteappserver.global.exception.Exception403;
import org.example.noteappserver.model.member.Member;
import org.example.noteappserver.model.note.Note;
import org.example.noteappserver.model.note.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteResponseDTO getNoteById(Long noteId, Long principalId) {
        Note note = getNoteIfExists(noteId);
        validateUserAccess(note, principalId);
        return new NoteResponseDTO(note);
    }

    public NoteListResponseDTO getAllNotesByUser(Long principalId) {
        List<Note> noteList = noteRepository.findByMemberId(principalId);
        return new NoteListResponseDTO(noteList);
    }

    @Transactional
    public void createNote(CreateNoteRequestDTO requestDTO, Member principal) {
        Note note = Note.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .memberId(principal.getId())
                .build();

        noteRepository.save(note);
    }

    @Transactional
    public void updateNote(UpdateNoteRequestDTO updateNoteRequestDTO, Long noteId, Long principalId) {
        Note note = getNoteIfExists(noteId);
        validateUserAccess(note, principalId);

        note.setTitle(updateNoteRequestDTO.getTitle());
        note.setContent(updateNoteRequestDTO.getContent());

        noteRepository.update(note);
    }

    @Transactional
    public void deleteNoteById(Long noteId, Long principalId) {
        Note note = getNoteIfExists(noteId);
        validateUserAccess(note, principalId);

        noteRepository.deleteById(noteId);
    }

    private Note getNoteIfExists(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new Exception400("해당 노트가 존재하지 않습니다."));
    }

    private void validateUserAccess(Note note, Long principalId) {
        if (!note.getMemberId().equals(principalId)) {
            throw new Exception403("접근 권한이 없습니다.");
        }
    }
}
