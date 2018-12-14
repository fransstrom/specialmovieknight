package com.movieknight.movieknight.Controllers.RestControllers;

import com.movieknight.movieknight.Database.entities.UnavailableDateTime;
import com.movieknight.movieknight.Database.entities.User1;
import com.movieknight.movieknight.Database.repositories.UnavalibleDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Dates {
    @Autowired
    UnavalibleDateRepository unavalibleDateRepository;

    @RequestMapping("/dates/reserved")
    public Iterable<UnavailableDateTime> dates() {
        return unavalibleDateRepository.findAll();
    }

}
