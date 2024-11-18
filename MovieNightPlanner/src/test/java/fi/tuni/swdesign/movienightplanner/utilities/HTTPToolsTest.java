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
        String url = "";
        HTTPTools instance = new HTTPTools();
        String expResult = "";
        String result = instance.makeGenericHttpRequest(url);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeTMOTNHttpRequest method, of class HTTPTools.
     */
    @Test
    public void testMakeTMOTNHttpRequest() throws Exception {
        System.out.println("makeTMOTNHttpRequest");
        String url = "";
        HTTPTools instance = new HTTPTools();
        String expResult = "";
        String result = instance.makeTMOTNHttpRequest(url);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
