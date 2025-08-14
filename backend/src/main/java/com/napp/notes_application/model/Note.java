package com.napp.notes_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @Column(length = 2000)
    private String content;

    @ManyToOne(cascade={CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    private User user;

}
