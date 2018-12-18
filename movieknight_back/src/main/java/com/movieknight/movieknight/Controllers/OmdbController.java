package com.movieknight.movieknight.Controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.movieknight.movieknight.OmdbAPI.OMDBException;
import com.movieknight.movieknight.OmdbAPI.OmdbApi;
import com.movieknight.movieknight.OmdbAPI.model.OmdbVideoFull;
import com.movieknight.movieknight.OmdbAPI.model.SearchResults;
import com.movieknight.movieknight.OmdbAPI.tools.OmdbBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OmdbController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin
    @RequestMapping("/omdb/movies/search/")
    public SearchResults searchResults(@RequestParam(value="s", defaultValue="Shrek") String search) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        SearchResults results = omdb.search(new OmdbBuilder().setSearchTerm(search).setTypeMovie().build());
        return results;
    }

    @CrossOrigin
    @RequestMapping("/omdb/movies/get/")
    public OmdbVideoFull omdbVideoFull(@RequestParam(value="s", defaultValue="tt0126029") String search) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        OmdbVideoFull results = omdb.getInfo(new OmdbBuilder().setImdbId(search).setTypeMovie().build());
        return results;
    }
}