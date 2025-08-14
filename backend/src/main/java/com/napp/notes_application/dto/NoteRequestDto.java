package com.napp.notes_application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Note content is required")
    private String content;
}
