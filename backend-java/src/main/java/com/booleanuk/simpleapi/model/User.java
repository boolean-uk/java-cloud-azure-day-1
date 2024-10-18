package com.booleanuk.simpleapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "borrower")
    private List<Borrowable> borrowedItems;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        borrowedItems = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isValid() {
        if (username.isBlank() || username.length() > 20) {
            return false;
        } else if (email.isBlank() || email.length() > 50) {
            return false;
        } else if (password == null || password.length() < 4 || password.length() > 120) {
            return false;
        }
        return true;
    }
}
