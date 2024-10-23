/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 *
 * @author janii, Copilot
 */
import java.util.List;
import com.google.gson.annotations.Expose;

public class MovieDetails {

    @Expose
    private boolean adult;
    @Expose
    private String backdrop_path;
    @Expose
    private Object belongs_to_collection;
    @Expose
    private int budget;
    @Expose
    private List<Genre> genres;
    @Expose
    private String homepage;
    @Expose
    private int id;
    @Expose
    private String imdb_id;
    @Expose
    private List<String> origin_country;
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
    private List<ProductionCompany> production_companies;
    @Expose
    private List<ProductionCountry> production_countries;
    @Expose
    private String release_date;
    @Expose
    private int revenue;
    @Expose
    private int runtime;
    @Expose
    private List<SpokenLanguage> spoken_languages;
    @Expose
    private String status;
    @Expose
    private String tagline;
    @Expose
    private String title;
    @Expose
    private boolean video;
    @Expose
    private double vote_average;
    @Expose
    private int vote_count;

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
     * Gets the collection to which the movie belongs.
     *
     * @return the collection object
     */
    public Object getBelongsToCollection() {
        return belongs_to_collection;
    }

    /**
     * Sets the collection to which the movie belongs.
     *
     * @param belongs_to_collection the collection object to set
     */
    public void setBelongsToCollection(Object belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    /**
     * Gets the budget of the movie.
     *
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the movie.
     *
     * @param budget the budget to set
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Gets the list of genres for the movie.
     *
     * @return the list of genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres for the movie.
     *
     * @param genres the list of genres to set
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Gets the homepage URL of the movie.
     *
     * @return the homepage URL
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Sets the homepage URL of the movie.
     *
     * @param homepage the homepage URL to set
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
     * Gets the IMDb ID of the movie.
     *
     * @return the IMDb ID
     */
    public String getImdbId() {
        return imdb_id;
    }

    /**
     * Sets the IMDb ID of the movie.
     *
     * @param imdb_id the IMDb ID to set
     */
    public void setImdbId(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    /**
     * Gets the list of origin countries for the movie.
     *
     * @return the list of origin countries
     */
    public List<String> getOriginCountry() {
        return origin_country;
    }

    /**
     * Sets the list of origin countries for the movie.
     *
     * @param origin_country the list of origin countries to set
     */
    public void setOriginCountry(List<String> origin_country) {
        this.origin_country = origin_country;
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
     * Gets the list of production companies for the movie.
     *
     * @return the list of production companies
     */
    public List<ProductionCompany> getProductionCompanies() {
        return production_companies;
    }

    /**
     * Sets the list of production companies for the movie.
     *
     * @param production_companies the list of production companies to set
     */
    public void setProductionCompanies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    /**
     * Gets the list of production countries for the movie.
     *
     * @return the list of production countries
     */
    public List<ProductionCountry> getProductionCountries() {
        return production_countries;
    }

    /**
     * Sets the list of production countries for the movie.
     *
     * @param production_countries the list of production countries to set
     */
    public void setProductionCountries(List<ProductionCountry> production_countries) {
        this.production_countries = production_countries;
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
     * Gets the revenue of the movie.
     *
     * @return the revenue
     */
    public int getRevenue() {
        return revenue;
    }

    /**
     * Sets the revenue of the movie.
     *
     * @param revenue the revenue to set
     */
    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    /**
     * Gets the runtime of the movie.
     *
     * @return the runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Sets the runtime of the movie.
     *
     * @param runtime the runtime to set
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Gets the list of spoken languages for the movie.
     *
     * @return the list of spoken languages
     */
    public List<SpokenLanguage> getSpokenLanguages() {
        return spoken_languages;
    }

    /**
     * Sets the list of spoken languages for the movie.
     *
     * @param spoken_languages the list of spoken languages to set
     */
    public void setSpokenLanguages(List<SpokenLanguage> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    /**
     * Gets the status of the movie.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the movie.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the tagline of the movie.
     *
     * @return the tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * Sets the tagline of the movie.
     *
     * @param tagline the tagline to set
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
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
     * Gets the vote average of the movie.
     *
     * @return the vote average
     */
    public double getVoteAverage() {
        return vote_average;
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
     * Gets the formatted runtime of the movie.
     *
     * @return the formatted runtime as a string
     */
    public String getFormattedRuntime() {
        int hours = runtime / 60;
        int minutes = runtime % 60;
        if (hours == 0) {
            return String.format("%dmin", minutes);
        }
        return String.format("%dh %dm", hours, minutes);
    }

    /**
     * Gets the formatted specifications of the movie, including runtime and
     * genre.
     *
     * @return the formatted specifications as a string
     */
    public String getFormattedSpecs() {
        if (genres != null && genres.size() > 0) {
            return String.format("%s · %s", getFormattedRuntime(), genres.get(0).getName());
        } else {
            return release_date;
        }
    }

}