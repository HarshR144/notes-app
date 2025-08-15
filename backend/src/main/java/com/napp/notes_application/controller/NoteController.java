package com.napp.notes_application.controller;

import com.napp.notes_application.dto.NoteRequestDto;
import com.napp.notes_application.dto.NoteResponseDto;
import com.napp.notes_application.model.User;
import com.napp.notes_application.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    private UUID extractUserId(Authentication auth) {
        return ((User)auth.getPrincipal()).getId(); // comes from JWT subject
    }


    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> findNotesByUserId(Authentication auth){
        final UUID id = extractUserId(auth);
        List<NoteResponseDto> list = noteService.findNotesByUserId(id);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@RequestBody NoteRequestDto noteRequestDto, Authentication auth){
        final UUID userId = extractUserId(auth);
        NoteResponseDto noteResponseDto = noteService.saveNote(noteRequestDto, userId);
        return ResponseEntity.ok().body(noteResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable UUID id,
                                      @RequestBody NoteRequestDto noteRequestDto){
        NoteResponseDto noteResponseDto = noteService.updateNote(id,noteRequestDto);

        return ResponseEntity.ok().body(noteResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id){
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
