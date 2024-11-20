/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.GenresResponse;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.models.SpokenLanguage;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import java.util.List;
import java.util.Map;
import org.apache.hc.client5.http.HttpResponseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author janii
 */
public class MovieDataControllerTest {
    
    private MovieDataController movieDataController;
    
    public MovieDataControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        movieDataController = new MovieDataController();
    }
    
    @AfterEach
    public void tearDown() {
    }


    /**
     * Test of fetchStreamingProviders method, of class MovieDataController.
     */
    @Test
    public void testFetchStreamingProviders() throws Exception {
        System.out.println("fetchStreamingProviders");
        try {
            movieDataController.fetchStreamingProviders();
            Map<Integer, StreamingProvider> streamProviderMap = movieDataController.getStreamProviderMap();
            assertNotNull(streamProviderMap);
            assertFalse(streamProviderMap.isEmpty());
        } catch (HttpResponseException e) {
            fail("HttpResponseException was thrown: " + e.getMessage());
        }
    }

    /**
     * Test of fetchMovieDetails method, of class MovieDataController.
     */
    @Test
    public void testFetchMovieDetails() {
        System.out.println("fetchMovieDetails");
        Movie movie = new Movie();
        movie.setId(603692);
        movieDataController.fetchMovieDetails(movie);
        assertNotNull(movie.getMovieDetails());
    }

    /**
     * Test of fetchMovieCredits method, of class MovieDataController.
     */
    @Test
    public void testFetchMovieCredits() {
        System.out.println("fetchMovieCredits");
        Movie movie = new Movie();
        movie.setId(603692);
        movieDataController.fetchMovieCredits(movie);
        assertNotNull(movie.getCredits());
    }

    /**
     * Test of fetchGenres method, of class MovieDataController.
     */
    @Test
    public void testFetchGenres() throws Exception {
        System.out.println("fetchGenres");
        String url = "https://api.themoviedb.org/3/genre/movie/list";
        try {
            GenresResponse response = movieDataController.fetchGenres(url);
            assertNotNull(response);
            assertNotNull(response.getGenres());
        } catch (HttpResponseException e) {
            fail("HttpResponseException was thrown: " + e.getMessage());
        }
    }
    
}
