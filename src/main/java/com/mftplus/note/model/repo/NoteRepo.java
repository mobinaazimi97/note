package com.mftplus.note.model.repo;

import com.mftplus.note.model.entity.Note;
import com.mftplus.note.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    List<Note> findByCategory(String category);

    List<Note> findAllByUser(User owner);

    List<Note> findByTitle(String title);
}
