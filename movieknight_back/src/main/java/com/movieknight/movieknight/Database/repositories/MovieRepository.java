package com.movieknight.movieknight.Database.repositories;

import com.movieknight.movieknight.Database.entities.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {
MovieEntity findById(String id);
}

