package com.napp.notes_application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoteResponseDto {


    private String id;
    private String title;
    private String content;
}
