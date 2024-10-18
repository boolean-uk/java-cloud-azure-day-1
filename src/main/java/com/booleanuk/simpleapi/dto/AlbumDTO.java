package com.booleanuk.simpleapi.dto;

import com.booleanuk.simpleapi.model.Artist;
import com.booleanuk.simpleapi.model.Song;
import lombok.Getter;

import java.util.List;

@Getter
public class AlbumDTO {
    private String albumName;
    private List<Integer> songListIds;
    private Integer artistId;

}
