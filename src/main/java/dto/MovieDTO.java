package dto;

import entities.Movie;

public class MovieDTO {
    
    private int year;
    private String name;
    private String[] actors;
    private int ageRating;

    public MovieDTO(Movie movie) {
        this.year = movie.getYear();
        this.name = movie.getName();
        this.actors = movie.getActors();
        this.ageRating = movie.getAgeRating();
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public String[] getActors() {
        return actors;
    }

    public int getAgeRating() {
        return ageRating;
    }
    
}
