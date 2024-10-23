package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Represents the details of a movie.
 */
public class MovieDetails {
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
    private String release_date;
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
     * Returns the list of genres of the movie.
     *
     * @return the list of genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres of the movie.
     *
     * @param genres the list of genres to set
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Returns the homepage URL of the movie.
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
     * Returns the ID of the movie.
     *
     * @return the ID of the movie
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the IMDb ID of the movie.
     *
     * @return the IMDb ID of the movie
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
     * Returns the list of origin countries of the movie.
     *
     * @return the list of origin countries
     */
    public List<String> getOriginCountry() {
        return origin_country;
    }

    /**
     * Sets the list of origin countries of the movie.
     *
     * @param origin_country the list of origin countries to set
     */
    public void setOriginCountry(List<String> origin_country) {
        this.origin_country = origin_country;
    }

    /**
     * Returns the original language of the movie.
     *
     * @return the original language of the movie
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
     * Returns the original title of the movie.
     *
     * @return the original title of the movie
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
     * Returns the overview of the movie.
     *
     * @return the overview of the movie
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
     * Returns the popularity of the movie.
     *
     * @return the popularity of the movie
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
     * Returns the release date of the movie.
     *
     * @return the release date of the movie
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
     * Returns the runtime of the movie in minutes.
     *
     * @return the runtime of the movie
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Sets the runtime of the movie in minutes.
     *
     * @param runtime the runtime to set
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Returns the list of spoken languages in the movie.
     *
     * @return the list of spoken languages
     */
    public List<SpokenLanguage> getSpokenLanguages() {
        return spoken_languages;
    }

    /**
     * Sets the list of spoken languages in the movie.
     *
     * @param spoken_languages the list of spoken languages to set
     */
    public void setSpokenLanguages(List<SpokenLanguage> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    /**
     * Returns the status of the movie.
     *
     * @return the status of the movie
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
     * Returns the tagline of the movie.
     *
     * @return the tagline of the movie
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
     * Returns the title of the movie.
     *
     * @return the title of the movie
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
     * Returns whether the movie is a video.
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
     * Returns the average vote of the movie.
     *
     * @return the average vote of the movie
     */
    public double getVoteAverage() {
        return vote_average;
    }

    /**
     * Sets the average vote of the movie.
     *
     * @param vote_average the average vote to set
     */
    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    /**
     * Returns the vote count of the movie.
     *
     * @return the vote count of the movie
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
     * Returns the formatted runtime of the movie.
     *
     * @return the formatted runtime of the movie
     */
    public String getFormattedRuntime() {
        int hours = runtime / 60;
        int minutes = runtime % 60;
        return String.format("%dh %02dm", hours, minutes);
    }

    /**
     * Returns the formatted specifications of the movie.
     *
     * @return the formatted specifications of the movie
     */
    public String getFormattedSpecs() {
        if (genres != null && genres.size() > 0) {
            return String.format("%s · %s", getFormattedRuntime(), genres.get(0).getName());
        } else {
            return release_date;
        }
    }
}