/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Make
 */
public class MovieLists {
    private static MovieLists instance;
    
    private List<Movie> popularMovieList;
    private List<Movie> topRatedMovieList;
    
    private MovieLists() {
        popularMovieList = new ArrayList<>();
        topRatedMovieList = new ArrayList<>();
    }
    
    public static synchronized MovieLists getInstance() {
        if (instance == null) {
            instance = new MovieLists();
        }
        return instance;
    }
    
    public List<Movie> getPopularMovieList() {
        return popularMovieList;
    }
    
    public void setPopularMovieList(List<Movie> popularMovieList) {
        this.popularMovieList = popularMovieList;
    }
    
    public boolean popularMovieListEmpty() {
        return popularMovieList == null || popularMovieList.isEmpty();
    }
    
    public List<Movie> getTopRatedMovieList() {
        return topRatedMovieList;
    }
    
    public void setTopRatedMovieList(List<Movie> topRatedMovieList) {
        this.topRatedMovieList = topRatedMovieList;
    }
    
    public boolean topRatedMovieListEmpty() {
        return topRatedMovieList == null || topRatedMovieList.isEmpty();
    }
}

