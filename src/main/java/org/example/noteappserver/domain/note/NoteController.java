package org.example.noteappserver.domain.note;

import lombok.RequiredArgsConstructor;
import org.example.noteappserver.domain.note.dto.CreateNoteRequestDTO;
import org.example.noteappserver.domain.note.dto.NoteListResponseDTO;
import org.example.noteappserver.domain.note.dto.NoteResponseDTO;
import org.example.noteappserver.domain.note.dto.UpdateNoteRequestDTO;
import org.example.noteappserver.domain.note.service.NoteService;
import org.example.noteappserver.global.common.dto.CommonResponseDTO;
import org.example.noteappserver.global.security.CustomMemberDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequestDTO requestDTO, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        noteService.createNote(requestDTO, customMemberDetails.getMember());
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "노트 생성"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllNotesByUser(@AuthenticationPrincipal CustomMemberDetails customMemberDetails){
        NoteListResponseDTO responseDTO = noteService.getAllNotesByUser(customMemberDetails.getMember().getId());
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "노트 목록 조회", responseDTO), HttpStatus.OK);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<?> getNoteById(@PathVariable("noteId") Long noteId, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        NoteResponseDTO responseDTO = noteService.getNoteById(noteId, customMemberDetails.getMember().getId());
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "노트 조회", responseDTO), HttpStatus.OK);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable("noteId") Long noteId, @RequestBody UpdateNoteRequestDTO requestDTO, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        noteService.updateNote(requestDTO, noteId, customMemberDetails.getMember().getId());
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "노트 수정"), HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable("noteId") Long noteId, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        noteService.deleteNoteById(noteId, customMemberDetails.getMember().getId());
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "노트 삭제"), HttpStatus.OK);
    }
}
