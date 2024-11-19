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
import fi.tuni.swdesign.movienightplanner.utilities.Constants;
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
    private Constants constants;
    
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
        constants = new Constants();
    }
    
    @AfterEach
    public void tearDown() {
    }

//    /**
//     * Test of fetchMoviesResponse method, of class MovieDataController.
//     */
//    @Test
//    public void testFetchMoviesResponse() throws Exception {
//        
//        System.out.println("fetchMoviesResponse");
//        String url = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", constants.getProvidersString());
// 
//        try {
//            MoviesResponse response = movieDataController.fetchMoviesResponse(url);
//            assertNotNull(response);
//            assertNotNull(response.getResults());
//        } catch (Exception e) {
//            fail("Exception was thrown: " + e.getMessage());
//        }
//    }

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
