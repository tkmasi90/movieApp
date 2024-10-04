/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;

/**
 * 
 * @author janii
 */
public class AppState {
    
    @Expose
    private ArrayList<Movie> searchHistory;
    
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
    
}
