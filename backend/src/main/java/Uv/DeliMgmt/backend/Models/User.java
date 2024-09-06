package Uv.DeliMgmt.backend.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import Uv.DeliMgmt.backend.Models.Role;

// Getters and setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Asegúrate de usar EnumType.STRING o EnumType.ORDINAL según corresponda
    @Column(name = "role")
    private Role role;

    // Otros campos y métodos getters/setters
}

