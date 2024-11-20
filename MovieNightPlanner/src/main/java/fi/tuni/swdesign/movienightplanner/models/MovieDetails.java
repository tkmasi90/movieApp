package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Represents the details of a movie.
 *
 * @author kian, Copilot
 */
public class MovieDetails extends Movie {
    @Expose
    private List<Genre> genres;
    @Expose
    private String homepage;
    @Expose
    private String imdb_id;
    @Expose
    private List<String> origin_country;
    @Expose
    private int runtime;
    @Expose
    private List<SpokenLanguage> spoken_languages;
    @Expose
    private String status;
    @Expose
    private String tagline;

    /**
     * Returns the list of genres of the movie.
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
     * Returns the runtime of the movie in minutes.
     *
     * @return the runtime of the movie
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
            return getReleaseDate();
        }
    }

}
