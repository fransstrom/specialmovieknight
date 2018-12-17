package com.movieknight.movieknight.Database.repositories;

import com.movieknight.movieknight.Database.entities.User1;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User1, Integer> {
     User1 findByEmail(String email);
}