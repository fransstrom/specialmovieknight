package com.movieknight.movieknight.Database.entities;

import com.google.api.client.util.DateTime;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"startDateTime", "endDateTime"}))
public class UnavailableDateTime {

    private Integer id;

    private String startDateTime;

    private String endDateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String date) {
        this.startDateTime = date;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDate) {
        this.endDateTime = endDate;
    }
}
