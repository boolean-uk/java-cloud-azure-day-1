package com.booleanuk.simpleapi.controllers;

import com.booleanuk.simpleapi.models.Artist;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
public class ArtistController extends GenericController<Artist, Integer> {
}
