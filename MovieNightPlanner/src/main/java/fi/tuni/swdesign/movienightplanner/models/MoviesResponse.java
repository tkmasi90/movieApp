/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Model for all APIs that return a single movie or multiple movies.
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;
import java.util.List;

public class MoviesResponse {
    @Expose
    private int page;
    @Expose
    private List<Movie> results;
    @Expose
    private int total_pages;
    @Expose
    private int total_results;

    /**
     * Returns the page number of the response.
     *
     * @return the page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the page number of the response.
     *
     * @param page the page number to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Returns the list of movies in the response.
     *
     * @return the list of movies
     */
    public List<Movie> getResults() {
        return results;
    }

    /**
     * Sets the list of movies in the response.
     *
     * @param results the list of movies to set
     */
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    /**
     * Returns the total number of pages in the response.
     *
     * @return the total number of pages
     */
    public int getTotalPages() {
        return total_pages;
    }

    /**
     * Sets the total number of pages in the response.
     *
     * @param total_pages the total number of pages to set
     */
    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }

    /**
     * Returns the total number of results in the response.
     *
     * @return the total number of results
     */
    public int getTotalResults() {
        return total_results;
    }

    /**
     * Sets the total number of results in the response.
     *
     * @param total_results the total number of results to set
     */
    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }
}