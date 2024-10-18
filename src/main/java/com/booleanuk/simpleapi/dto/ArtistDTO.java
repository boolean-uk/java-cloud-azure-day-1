package com.booleanuk.simpleapi.dto;

import com.booleanuk.simpleapi.model.Album;
import com.booleanuk.simpleapi.model.Song;
import lombok.Getter;

import java.util.List;

@Getter
public class ArtistDTO {
    private String artistName;
    private String dob;
}
