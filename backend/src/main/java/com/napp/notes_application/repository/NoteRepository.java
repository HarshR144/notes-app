package com.napp.notes_application.repository;

import com.napp.notes_application.model.Note;
import com.napp.notes_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByUserId(UUID userId);
}
