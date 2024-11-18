/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

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
public class HTTPToolsTest {
    
    public HTTPToolsTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of makeGenericHttpRequest method, of class HTTPTools.
     */
    @Test
    public void testMakeGenericHttpRequest() throws Exception {
        System.out.println("makeGenericHttpRequest");
        String url = String.format("https://api.themoviedb.org/3/movie/%s?language=en-US", 603692);
        HTTPTools instance = new HTTPTools();
        Boolean expResult = true;
        String searchFor = "John Wick";
        String result = instance.makeGenericHttpRequest(url);
        Boolean contains = result.contains(searchFor);
        
        assertEquals(expResult, contains);
    }

    /**
     * Test of makeTMOTNHttpRequest method, of class HTTPTools.
     */
    @Test
    public void testMakeTMOTNHttpRequest() throws Exception {
        System.out.println("makeTMOTNHttpRequest");
        
        String urlPre = "https://streaming-availability.p.rapidapi.com/shows/movie/";
        String urlPost = "?series_granularity=show&output_language=en";
        String url = urlPre + Integer.toString(603692) + urlPost;
        HTTPTools instance = new HTTPTools();
        Boolean expResult = true;
        String searchFor = "John Wick";
        String result = instance.makeTMOTNHttpRequest(url);
        Boolean contains = result.contains(searchFor);
        assertEquals(expResult, contains);
    }
    
}
