/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

/**
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;
import java.util.List;

public class MovieSearchResponse {
    @Expose
    private int page;
    @Expose
    private List<Movie> results;
    @Expose
    private int total_pages;
    @Expose
    private int total_results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }

    
}
