package com.movieknight.movieknight.Database.repositories;

import com.movieknight.movieknight.Database.entities.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Booking findById(String id);
}
