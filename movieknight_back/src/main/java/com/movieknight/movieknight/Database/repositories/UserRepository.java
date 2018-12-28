package com.movieknight.movieknight.Database.repositories;

import com.movieknight.movieknight.Database.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}