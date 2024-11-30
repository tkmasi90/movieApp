/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.utilities.MovieGenres;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for application state-related features.
 * 
 * @author janii, kian, Copilot
 */
public class AppState {
    
    /**
     * List of movies that have been searched by the user.
     */
    @Expose
    private final ArrayList<Movie> searchHistory;
    
    /**
     * Map of movie IDs to their corresponding user ratings.
     */
    @Expose
    private final Map<Integer, Integer> movieRatings;

    /**
     * Map of movie IDs to their corresponding movie objects.
     */
    @Expose
    private final Map<Integer, Movie> movies;

    /**
     * List of movies that have been watched by the user.
     */
    @Expose
    private final List<Movie> watchHistory;
    
    /**
     * List of preferred streaming providers.
     */
    @Expose
    private final List<String> prefProviders;
    
    /**
     * List of preferred genres by their IDs.
     */
    @Expose
    private final List<Integer> prefGenres;
    
    /**
     * List of preferred audio settings by their IDs.
     */
    @Expose
    private final List<Integer> prefAudio;
    
    /**
     * Minimum rating preference set by the user.
     */
    @Expose
    private Integer prefMinRating;

    public AppState() {
        this.searchHistory = new ArrayList<>();
        this.movieRatings = new HashMap<>();
        this.movies = new HashMap<>();
        this.watchHistory = new ArrayList<>();
        this.prefProviders = new ArrayList<>();
        this.prefGenres = new ArrayList<>();
        this.prefAudio = new ArrayList<>();
    }

    /**
    * Returns the user's movie search history.
    *
    * @return searchHistory 
    */
    public List<Movie> getSearchHistory(){
        List<Movie> searchHistoryReversed = (List <Movie>) searchHistory.clone();
        Collections.reverse(searchHistoryReversed);
        return searchHistoryReversed;
    }

    /**
    * Adds a movie to the user's search history.
    *
    * @param movieSearch the movie to be added
    */    
    public void addSearchedMovie(Movie movieSearch){
        if(!searchHistory.contains(movieSearch)) {
            this.searchHistory.add(movieSearch);
        }
    }
    
    /**
    * Sets streaming providers to the user's preferences.
    *
    * @param providers streaming providers to be added
    */   
    public void setPrefProviders(List<String> providers) {
        this.prefProviders.clear();
        this.prefProviders.addAll(providers);
    }
    
    /**
    * Sets preferred genres to the user's preferences.
    *
    * @param genres genres to be added
    */
    public void setPrefGenres(List<Integer> genres) {
        this.prefGenres.clear();
        this.prefGenres.addAll(genres);
    }

    /**
    * Sets preferred audio language to the user's preferences.
    *
    * @param audios audios to be added
    */
    public void setPrefAudio(List<Integer> audios) {
        this.prefAudio.clear();
        this.prefAudio.addAll(audios);
    }
    
    /**
    * Sets preferred minimum rating to the user's preferences.
    *
    * @param minRating minimum rating to be added
    */    
    public void setPrefMinRating(Integer minRating) {
        this.prefMinRating = minRating;
    }
    
    /**
    * Gets preferred providers from the user's preferences.
     * @return streaming services
    */  
    public List<String> getPrefProviders() {
        return prefProviders;
    }
    
    /**
    * Gets preferred genres from the user's preferences.
     * @return genres
    */  
    public List<Integer> getPrefGenres() {
        return prefGenres;
    }
    
    /**
    * Gets preferred audio languages from the user's preferences.
     * @return audio languages
    */  
    public List<Integer> getPrefAudio() {
        return prefAudio;
    }

    /**
    * Gets preferred minimum rating from the user's preferences.
     * @return minimum rating
    */      
    public Integer getPrefMinRating() {
        return prefMinRating;
    }
    
    /**
    * Saves the rating for a movie.
    *
    * @param movie the movie to be rated
    * @param rating the rating to be saved
    */
    public void saveMovieRating(Movie movie, int rating) {
        this.movies.put(movie.getId(), movie);
        this.movieRatings.put(movie.getId(), rating);
        this.watchHistory.add(movie);
    }

    /**
    * Gets the rating for a movie.
    *
    * @param movie the movie to get the rating for
    * @return the rating, or -1 if the movie has not been rated
    */
    public int getMovieRating(Movie movie) {
        return this.movieRatings.getOrDefault(movie.getId(), -1);
    }

    /**
     * Gets all movie ratings.
     *
     * @return movieRatings
     */
    public Map<Integer, Integer> getMovieRatings() {
        return this.movieRatings;
    }

    /**
     * Gets the movies that have been rated.
     *
     * @return a list of rated movies
     */
    public List<String> getWatchHistory() {
        List<String> watchHistoryStr = new ArrayList<>();
        for (Movie movie : this.watchHistory) {
            watchHistoryStr.add(movie.getFormattedTitle());
        }
        return watchHistoryStr;
    }
    
        /**
     * Gets a movie from the history.
     *
     * @param movieName movie name to fetch from history list
     * @return a list of rated movies
     */
    public Movie getMovieFromHistory(String movieName) {
        List<String> watchHistory = new ArrayList<>();
        for (Movie movie : this.watchHistory) {
            if(movie.getFormattedTitle().equals(movieName)){
                return movie;
            }
        }
        return null;
    }

    /**
     * Gets the genres of rated movies.
     *
     * @param minRating the minimum rating to include
     * @return a list of genre names
     */
    public List<String> getRatedMovieGenresByRating(int minRating) {
        List<String> genres = new ArrayList<>();
        for (Movie movie : movies.values()) {
          if(movie.getVoteAverage() >= minRating)
            for (Integer genreId : movie.getGenreIds()) {
              String genreName = MovieGenres.getGenreNameById(genreId);
              if (genreName != null) {
                  genres.add(genreName);
              }
            }
        }
        return genres;
    }

    /**
     * Gets the century categories of rated movies.
     *
     * @param minRating the minimum rating to include
     * @return a list of century categories (e.g., "90's", "00's")
     */
    public List<String> getMoviesByCentury(int minRating) {
        List<String> centuries = new ArrayList<>();
        for (Movie movie : movies.values()) {
          if(movie.getVoteAverage() >= minRating) {
            String releaseDate = movie.getReleaseDate();
            if (releaseDate != null && releaseDate.length() >= 4) {
              int year = Integer.parseInt(releaseDate.substring(0, 4));
              int decade = (year % 100) / 10 * 10;
              String century = String.format("%02d's", decade);
              centuries.add(century);
            } else {
              centuries.add("Unknown");
            }
          }
        }
        return centuries;
    }

}
