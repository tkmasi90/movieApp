/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Model class for the genres-API-call.
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;
import java.util.List;

public class GenresResponse {

    @Expose
    private List<Genre> genres;

    /**
     * Gets the list of genres.
     *
     * @return the list of genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres.
     *
     * @param genres the list of genres to set
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

}
