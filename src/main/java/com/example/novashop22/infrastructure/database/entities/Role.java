package com.example.novashop22.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    private long id;
    @Column(name = "role_name")
    private String role;
}
