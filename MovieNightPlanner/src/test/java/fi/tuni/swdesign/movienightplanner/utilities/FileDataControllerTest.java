/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import java.io.FileNotFoundException;
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
public class FileDataControllerTest {
    
    public FileDataControllerTest() {
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
    * Test of readStateFromFile method, of class FileDataController, using
    * a non-existent filename.
    */
    @Test
    public void testReadFromFileFileNotFound() throws FileNotFoundException {
        System.out.println("readFromFileFileNotFound");
        String fileName = "THIS_FILE_SHOULD_NOT_EXIST";
        FileDataController instance = new FileDataController();
        
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            instance.readStateFromFile(fileName);
        });
        
        String exceptedMessage = "The system cannot find the file specified";
        String actualMessage = exception.getMessage();
        
        assertTrue(actualMessage.contains(exceptedMessage));
    }
    
    /**
    * Test of writeStateToFile method, of class FileDataController.
    */
    @Test
    public void testWriteStateToFile() throws Exception {
        System.out.println("writeStateToFile");
        Movie testMovie = new Movie();
        testMovie.setTitle("Testielokuva");
        AppState appState = new AppState();
        appState.addSearchedMovie(testMovie);
        String filename = "testfile.json";
        FileDataController instance = new FileDataController();
        boolean expResult = true;
        boolean result = instance.writeStateToFile(appState, filename);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of readStateFromFile method, of class FileDataController.
     */
    @Test
    public void testReadStateFromFile() throws Exception {
        System.out.println("readStateFromFile");
        Movie testMovie = new Movie();
        testMovie.setTitle("Testielokuva");
        AppState appState = new AppState();
        appState.addSearchedMovie(testMovie);
        String filename = "testfile.json";
        FileDataController instance = new FileDataController();
        instance.writeStateToFile(appState, filename);
        String movieTitle = "Testielokuva";
        AppState result = instance.readStateFromFile(filename);
        assertEquals(movieTitle, result.getSearchHistory().get(0).getTitle());
    }


    
}
