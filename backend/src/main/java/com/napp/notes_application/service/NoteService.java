package com.napp.notes_application.service;

import com.napp.notes_application.dto.NoteRequestDto;
import com.napp.notes_application.dto.NoteResponseDto;
import com.napp.notes_application.exception.NoteNotFoundException;
import com.napp.notes_application.model.Note;
import com.napp.notes_application.model.User;
import com.napp.notes_application.repository.NoteRepository;
import com.napp.notes_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository){
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<NoteResponseDto> findNotesByUserId(UUID userId){
        return noteRepository.findByUserId(userId)
                .stream()
                .map(note->
                new NoteResponseDto(note.getId().toString(),note.getTitle(),note.getContent())
        ).collect(Collectors.toList());
    }

    public NoteResponseDto findNoteById(UUID noteId){
        Note note =  noteRepository.findById(noteId)
                .orElseThrow(()-> new NoteNotFoundException("Note not found for id:" + noteId));

        return new NoteResponseDto(note.getId().toString(),note.getTitle(), note.getContent());
    }


    public NoteResponseDto saveNote(NoteRequestDto noteRequestDto,UUID userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new NoteNotFoundException("User not found"));
        Note note = new Note();
        note.setUser(user);
        note.setTitle(noteRequestDto.getTitle());
        note.setContent(noteRequestDto.getContent());
        Note updatedNote = noteRepository.save(note);
        return new NoteResponseDto(updatedNote.getId().toString(),updatedNote.getTitle(), updatedNote.getContent());
    }

    public NoteResponseDto updateNote(UUID id, NoteRequestDto noteRequestDto){
        Note note = noteRepository.findById(id).orElseThrow(
                ()-> new NoteNotFoundException("Note not found for id: "+ id)
        );

        note.setTitle(noteRequestDto.getTitle());
        note.setContent(noteRequestDto.getContent());
        Note updatedNote = noteRepository.save(note);
        return new NoteResponseDto(updatedNote.getId().toString(),updatedNote.getTitle(), updatedNote.getContent());
    }

    public void delete(UUID id){
        noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException("Note not found with id:"+ id));
        noteRepository.deleteById(id);
    }
}
