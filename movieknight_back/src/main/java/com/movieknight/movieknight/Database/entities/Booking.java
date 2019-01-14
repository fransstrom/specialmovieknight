package com.movieknight.movieknight.Database.entities;


import javax.persistence.*;

@Entity
@Table(

        uniqueConstraints =
        @UniqueConstraint(columnNames = {"startDate", "endDate"})
)
public class Booking {
    int Id;
    String startDate;
    String endDate;
    String movieTitle;
    String movieId;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
