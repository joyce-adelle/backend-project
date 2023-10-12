package com.crust.backendproject.models;

import com.crust.backendproject.util.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    @Column(name = "name", length = 60)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @NaturalId
    @Column(name = "email", length = 50, unique = true)
    private String email;

    @NotNull
    @Column(name = "is_verified", columnDefinition = "boolean default false")
    private Boolean isVerified;

    @JsonIgnore
    @NotBlank
    @Column(name = "password", length = 150)
    private String password;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

}
