package com.booleanuk.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {
    private int authorId;
    private String title;
    private String description;
    private String genre;
    private int rating;
}
