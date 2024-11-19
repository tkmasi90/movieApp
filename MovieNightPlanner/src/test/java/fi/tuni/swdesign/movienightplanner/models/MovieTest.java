/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
public class MovieTest {
    
    private Movie movie;
    
    public MovieTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        movie = new Movie();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isAdult method, of class Movie.
     */
    @Test
    public void testIsAdult() {
        System.out.println("isAdult");
        movie.setAdult(true);
        assertTrue(movie.isAdult());

        movie.setAdult(false);
        assertFalse(movie.isAdult());
    }

    /**

    /**
     * Test of getBackdropPath method, of class Movie.
     */
    @Test
    public void testGetBackdropPath() {
       movie.setBackdropPath("/path/to/backdrop.jpg");
       assertEquals("/path/to/backdrop.jpg", movie.getBackdropPath());
    }


    /**
     * Test of getGenreIds method, of class Movie.
     */
    @Test
    public void testGetGenreIds() {
        System.out.println("getGenreIds");
        List<Integer> genreIds = Arrays.asList(28, 12, 16);
        movie.setGenreIds(genreIds);
        assertEquals(genreIds, movie.getGenreIds());
    }



    /**
     * Test of getId method, of class Movie.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        movie.setId(12345);
        assertEquals(12345, movie.getId());
    }



    /**
     * Test of getOriginalLanguage method, of class Movie.
     */
    @Test
    public void testGetOriginalLanguage() {
        System.out.println("getOriginalLanguage");
        movie.setOriginalLanguage("en");
        assertEquals("en", movie.getOriginalLanguage());
    }



    /**
     * Test of getOriginalTitle method, of class Movie.
     */
    @Test
    public void testGetOriginalTitle() {
        System.out.println("getOriginalTitle");
        movie.setOriginalTitle("Original Title");
        assertEquals("Original Title", movie.getOriginalTitle());
    }



    /**
     * Test of getOverview method, of class Movie.
     */
    @Test
    public void testGetOverview() {
        System.out.println("getOverview");
        movie.setOverview("This is a movie overview.");
        assertEquals("This is a movie overview.", movie.getOverview());
    }



    /**
     * Test of getPopularity method, of class Movie.
     */
    @Test
    public void testGetPopularity() {
        System.out.println("getPopularity");
        movie.setPopularity(8.5);
        assertEquals(8.5, movie.getPopularity(), 0.01);
    }



    /**
     * Test of getPosterPath method, of class Movie.
     */
    @Test
    public void testGetPosterPath() {
        System.out.println("getPosterPath");
        movie.setPosterPath("/path/to/poster.jpg");
        assertEquals("/path/to/poster.jpg", movie.getPosterPath());
    }



    /**
     * Test of getReleaseDate method, of class Movie.
     */
    @Test
    public void testGetReleaseDate() {
        System.out.println("getReleaseDate");
        movie.setReleaseDate("2024-11-18");
        assertEquals("2024-11-18", movie.getReleaseDate());
    }



    /**
     * Test of getTitle method, of class Movie.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        movie.setTitle("Movie Title");
        assertEquals("Movie Title", movie.getTitle());
    }



    /**
     * Test of isVideo method, of class Movie.
     */
    @Test
    public void testIsVideo() {
        System.out.println("isVideo");
        movie.setVideo(true);
        assertTrue(movie.isVideo());

        movie.setVideo(false);
        assertFalse(movie.isVideo());
    }



    /**
     * Test of getVoteAverage method, of class Movie.
     */
    @Test
    public void testGetVoteAverage() {
        System.out.println("getVoteAverage");
        movie.setVoteAverage(7.8);
        assertEquals(7.8, movie.getVoteAverage(), 0.01);
    }



    /**
     * Test of getVoteCount method, of class Movie.
     */
    @Test
    public void testGetVoteCount() {
        System.out.println("getVoteCount");
        movie.setVoteCount(1000);
        assertEquals(1000, movie.getVoteCount());
    }



    /**
     * Test of addStreamingProvider method, of class Movie.
     */
    @Test
    public void testAddStreamingProvider() {
        System.out.println("addStreamingProvider");
        StreamingProvider provider = new StreamingProvider();
        provider.setProviderName("Netflix");
        movie.addStreamingProvider(provider);
        assertTrue(movie.getStreamingProviders().contains(provider));
    }

    /**
     * Test of getStreamingProviders method, of class Movie.
     */
    @Test
    public void testGetStreamingProviders() {
        System.out.println("getStreamingProviders");
        StreamingProvider provider1 = new StreamingProvider();
        provider1.setProviderName("Netflix");
       
        StreamingProvider provider2 = new StreamingProvider();
        provider2.setProviderName("Hulu");
        movie.addStreamingProvider(provider1);
        movie.addStreamingProvider(provider2);
        List<StreamingProvider> providers = movie.getStreamingProviders();
        assertEquals(2, providers.size());
        assertTrue(providers.contains(provider1));
        assertTrue(providers.contains(provider2));
    }

    /**
     * Test of getFormattedTitle method, of class Movie.
     */
    @Test
    public void testGetFormattedTitle() {
        System.out.println("getFormattedTitle");
        movie.setTitle("Movie Title");
        movie.setReleaseDate("2024-11-18");
        assertEquals("Movie Title (2024)", movie.getFormattedTitle());

        movie.setReleaseDate(null);
        assertEquals("Movie Title (Unknown)", movie.getFormattedTitle());
    }

    /**
     * Test of getMovieDetails method, of class Movie.
     */
    @Test
    public void testGetMovieDetails() {
        System.out.println("getMovieDetails");
       MovieDetails details = new MovieDetails();
        movie.setMovieDetails(details);
        assertEquals(details, movie.getMovieDetails());
    }


    /**
     * Test of getCredits method, of class Movie.
     */
    @Test
    public void testGetCredits() {
        System.out.println("getCredits");
        Credits credits = new Credits();
        movie.setCredits(credits);
        assertEquals(credits, movie.getCredits());
    }


    
}
