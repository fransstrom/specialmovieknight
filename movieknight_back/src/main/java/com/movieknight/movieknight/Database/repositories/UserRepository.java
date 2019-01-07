package com.movieknight.movieknight.Database.repositories;

import com.movieknight.movieknight.Database.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
User findById(long id);

    @Query(value = "select * from movienight.user where google_id = :google_id", nativeQuery=true)
    User findByGoogle_id(@Param("google_id") String google_id );
}