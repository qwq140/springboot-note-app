package org.example.noteappserver.model.note;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoteRepository {
    List<Note> findByMemberId(Long memberId);
    Optional<Note> findById(Long id);
    void save(Note note);
    void update(Note note);
    void deleteById(Long id);
}
