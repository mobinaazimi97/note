package com.mftplus.note.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "noteEntity")
@Table(name = "note_table")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category;
    @Column(name = "content")
    private String content;
    @Column(name = "createAt")
    private LocalDateTime createAt;
    @Column(name = "locked")
    private boolean locked = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
