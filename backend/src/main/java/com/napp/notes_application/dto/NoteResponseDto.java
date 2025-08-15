package com.napp.notes_application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NoteResponseDto {


    private String id;
    private String title;
    private String content;
}
