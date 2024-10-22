/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

import fi.tuni.swdesign.movienightplanner.models.Movie;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author janii
 */
public class AppState {
    
    @Expose
    private ArrayList<Movie> searchHistory;
    
    @Expose
    private Map<Integer, Integer> movieRatings;

    public AppState() {
        this.searchHistory = new ArrayList<>();
        this.movieRatings = new HashMap<>();
    }

    /**
    * Returns the user's movie search history.
    *
    * @return searchHistory 
    */
    public ArrayList<Movie> getSearchHistory(){
        return this.searchHistory;
    }

    /**
    * Adds a movie to the user's search history.
    *
    * @param movieSearch the movie to be added
    */    
    public void addSearchedMovie(Movie movieSearch){
        this.searchHistory.add(movieSearch);
    }

    /**
    * Saves the rating for a movie.
    *
    * @param movieId the ID of the movie
    * @param rating the rating to be saved
    */
    public void saveMovieRating(int movieId, int rating) {
        this.movieRatings.put(movieId, rating);
    }

    /**
    * Gets the rating for a movie.
    *
    * @param movieId the ID of the movie
    * @return the rating, or -1 if the movie has not been rated
    */
    public int getMovieRating(int movieId) {
        return this.movieRatings.getOrDefault(movieId, -1);
    }

    /**
     * Gets all movie ratings.
     *
     * @return movieRatings
     */
    public Map<Integer, Integer> getMovieRatings() {
        return this.movieRatings;
    }
}
