package com.booleanuk.simpleapi.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SongDTO {
    private String songName;
    private Integer duration;
    private List<Integer> genreIds;
    private Integer artistId;
    private Integer albumId;
}
