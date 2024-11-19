/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;

/**
 * Model class representing a movie.
 *
 * @author janii, Copilot
 */
public class Movie {

    @Expose
    private boolean adult;
    @Expose
    private String backdrop_path;
    @Expose
    private List<Integer> genre_ids;
    @Expose
    private int id;
    @Expose
    private String original_language;
    @Expose
    private String original_title;
    @Expose
    private String overview;
    @Expose
    private double popularity;
    @Expose
    private String poster_path;
    @Expose
    private String release_date;
    @Expose
    private String title;
    @Expose
    private boolean video;
    @Expose
    private double vote_average;
    @Expose
    private int vote_count;

    private ArrayList<StreamingProvider> streamingProviders;
    
    private MovieDetails movieDetails;
    private Credits credits;

    /**
     * Constructs a new Movie object with an empty list of streaming providers.
     */
    public Movie() {
        this.streamingProviders = new ArrayList<>();
    }

    /**
     * Checks if the movie is for adults.
     *
     * @return true if the movie is for adults, false otherwise
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * Sets whether the movie is for adults.
     *
     * @param adult true if the movie is for adults, false otherwise
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * Gets the backdrop path of the movie.
     *
     * @return the backdrop path
     */
    public String getBackdropPath() {
        return backdrop_path;
    }

    /**
     * Sets the backdrop path of the movie.
     *
     * @param backdrop_path the backdrop path to set
     */
    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /**
     * Gets the list of genre IDs for the movie.
     *
     * @return the list of genre IDs
     */
    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    /**
     * Sets the list of genre IDs for the movie.
     *
     * @param genre_ids the list of genre IDs to set
     */
    public void setGenreIds(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    /**
     * Gets the ID of the movie.
     *
     * @return the movie ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param id the movie ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the original language of the movie.
     *
     * @return the original language
     */
    public String getOriginalLanguage() {
        return original_language;
    }

    /**
     * Sets the original language of the movie.
     *
     * @param original_language the original language to set
     */
    public void setOriginalLanguage(String original_language) {
        this.original_language = original_language;
    }

    /**
     * Gets the original title of the movie.
     *
     * @return the original title
     */
    public String getOriginalTitle() {
        return original_title;
    }

    /**
     * Sets the original title of the movie.
     *
     * @param original_title the original title to set
     */
    public void setOriginalTitle(String original_title) {
        this.original_title = original_title;
    }

    /**
     * Gets the overview of the movie.
     *
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets the overview of the movie.
     *
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Gets the popularity of the movie.
     *
     * @return the popularity
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * Sets the popularity of the movie.
     *
     * @param popularity the popularity to set
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * Gets the poster path of the movie.
     *
     * @return the poster path
     */
    public String getPosterPath() {
        return poster_path;
    }

    /**
     * Sets the poster path of the movie.
     *
     * @param poster_path the poster path to set
     */
    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     * Gets the release date of the movie.
     *
     * @return the release date
     */
    public String getReleaseDate() {
        return release_date;
    }

    /**
     * Sets the release date of the movie.
     *
     * @param release_date the release date to set
     */
    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks if the movie is a video.
     *
     * @return true if the movie is a video, false otherwise
     */
    public boolean isVideo() {
        return video;
    }

    /**
     * Sets whether the movie is a video.
     *
     * @param video true if the movie is a video, false otherwise
     */
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * Gets the vote average of the movie, rounded to one decimal place.
     *
     * @return the vote average
     */
    public double getVoteAverage() {
        return Math.floor(vote_average * 10) / 10.0;
    }

    /**
     * Sets the vote average of the movie.
     *
     * @param vote_average the vote average to set
     */
    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    /**
     * Gets the vote count of the movie.
     *
     * @return the vote count
     */
    public int getVoteCount() {
        return vote_count;
    }

    /**
     * Sets the vote count of the movie.
     *
     * @param vote_count the vote count to set
     */
    public void setVoteCount(int vote_count) {
        this.vote_count = vote_count;
    }

    /**
     * Adds a streaming provider to the movie's list of streaming providers.
     *
     * @param provider the streaming provider to add
     */
    public void addStreamingProvider(StreamingProvider provider) {
        if (this.streamingProviders == null) {
            this.streamingProviders = new ArrayList<>();
        }
        this.streamingProviders.add(provider);
    }

    /**
     * Gets the list of streaming providers for the movie.
     *
     * @return the list of streaming providers
     */
    public List<StreamingProvider> getStreamingProviders() {
        return this.streamingProviders;
    }

    /**
     * Gets the formatted title of the movie, including the release year.
     *
     * @return the formatted title
     */
    public String getFormattedTitle() {
        String year = (release_date != null && release_date.length() >= 4) ? release_date.substring(0, 4) : "Unknown";
        return title + " (" + year + ")";
    }

    /**
     * Gets the detailed information of the movie.
     *
     * @return the movie details
     */
    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    /**
     * Sets the detailed information of the movie.
     *
     * @param movieDetails the movie details to set
     */
    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    /**
     * Gets the credits of the movie.
     *
     * @return the credits
     */
    public Credits getCredits() {
        return credits;
    }

    /**
     * Sets the credits of the movie.
     *
     * @param credits the credits to set
     */
    public void setCredits(Credits credits) {
        this.credits = credits;
    }
}
