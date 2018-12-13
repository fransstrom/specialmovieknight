package com.movieknight.movieknight.Database;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
     User findByEmail(String email);
}