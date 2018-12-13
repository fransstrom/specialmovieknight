package com.movieknight.movieknight.OmdbAPI;

import com.movieknight.movieknight.OmdbAPI.emumerations.*;
import com.movieknight.movieknight.OmdbAPI.model.*;
import com.movieknight.movieknight.OmdbAPI.tools.*;

public class Main {
    public static void main(String[] args) throws OMDBException {


        OmdbApi omdb = new OmdbApi("831f2756");



        SearchResults results = omdb.search(new OmdbBuilder().setSearchTerm("Shrek").build());
        SearchResults results2 = omdb.search(new OmdbBuilder().setTitle("Shrek").build());
        SearchResults results3 = omdb.search(new OmdbBuilder().setImdbId("Shrek").build());
        SearchResults results4 = omdb.search(new OmdbBuilder().setSearchTerm("Shrek").build());


        System.out.println(results4.getTotalResults());

        for (int i=0; i<results4.getTotalResults(); i++){
            if (results4.getResults().get(i).getTitle() instanceof String)
            System.out.println(results4.getResults().get(i).getTitle());
        }
    }
}
