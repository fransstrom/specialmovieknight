package com.movieknight.movieknight.Database.entities;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import javax.persistence.*;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"date"})})
public class UnavailableDate {


    private Integer id;


    public Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Column(name="date", unique = true)
    public Date getDate() {
        return date;
    }
}
