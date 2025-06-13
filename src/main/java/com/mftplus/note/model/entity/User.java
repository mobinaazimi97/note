package com.mftplus.note.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "userEntity")
@Table(name = "user_table")
public class User {
    @Id
    private String username;
    private String password;
    private String role;
}
