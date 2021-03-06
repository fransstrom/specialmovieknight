package com.movieknight.movieknight.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.movieknight.movieknight.Database.entities.MovieEntity;
import com.movieknight.movieknight.Database.repositories.MovieRepository;
import com.movieknight.movieknight.OmdbAPI.OMDBException;
import com.movieknight.movieknight.OmdbAPI.OmdbApi;
import com.movieknight.movieknight.OmdbAPI.model.OmdbVideoFull;
import com.movieknight.movieknight.OmdbAPI.model.SearchResults;
import com.movieknight.movieknight.OmdbAPI.tools.OmdbBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OmdbController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    MovieRepository movieRepository;
    /*@CrossOrigin
    @RequestMapping(value = "/omdb/movies/search/", method = RequestMethod.GET)
    public SearchResults searchResults(@RequestParam(value="s", defaultValue="Shrek") String search) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        SearchResults results = omdb.search(new OmdbBuilder().setSearchTerm(search).setTypeMovie().build());
        return results;
    }*/

    /*@CrossOrigin
    @RequestMapping(value = "/omdb/movies/get/", method = RequestMethod.GET)
    public OmdbVideoFull omdbVideoFull(@RequestParam(value="s", defaultValue="tt0126029") String search) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        OmdbVideoFull results = omdb.getInfo(new OmdbBuilder().setImdbId(search).setTypeMovie().build());
        return results;
    }*/

    @CrossOrigin
    @RequestMapping(value = "/omdb/movies/search/", method = RequestMethod.GET)
    public ResponseEntity<SearchResults> searchResultsWithStatusCode(@RequestParam(value = "s", defaultValue = "Shrek") String search) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        SearchResults results = omdb.search(new OmdbBuilder().setSearchTerm(search).setTypeMovie().build());
        if (search.toLowerCase().equals("kevinfrans")) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        } else if (results == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (results.getTotalResults() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/omdb/movies/get/", method = RequestMethod.GET)
    public ResponseEntity<OmdbVideoFull> omdbVideoFullWithStatusCode(@RequestParam(value = "s", defaultValue = "tt0126029") String search) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        OmdbVideoFull results = omdb.getInfo(new OmdbBuilder().setImdbId(search).setTypeMovie().build());
        if (results == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (results.getActors().toLowerCase().contains("nicolas cage")) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        } else {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/admin/addMovieToDatabase2/", method = RequestMethod.POST)
    public ResponseEntity<String> id(@RequestParam("id") String id) throws OMDBException {
        OmdbApi omdb = new OmdbApi("831f2756");
        OmdbVideoFull results = omdb.getInfo(new OmdbBuilder().setImdbId(id).setTypeMovie().build());
        if (results==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            MovieEntity movie=new MovieEntity();
            movie.setActors(results.getActors());
            movie.setDescription(results.getPlot());
            movie.setDuration(results.getRuntime());
            movie.setId(results.getImdbID());
            movie.setImdbRating(results.getImdbRating());
            movie.setReleaseDate(results.getReleased());
            movie.setTitle(results.getTitle());
            movie.setPosterUrl(results.getPoster());
            movie.setGenre(results.getGenre());
            movie.setDirectors(results.getDirector());
            movieRepository.save(movie);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getAllMovies", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<MovieEntity>> movies() {
        ArrayList<MovieEntity> movieEntities = (ArrayList<MovieEntity>) movieRepository.findAll();
        if (movieEntities==null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else
        return new ResponseEntity<>(movieEntities, HttpStatus.OK);
    }
}