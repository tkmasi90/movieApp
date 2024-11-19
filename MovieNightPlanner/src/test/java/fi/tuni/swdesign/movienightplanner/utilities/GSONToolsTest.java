/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.Gson;
import fi.tuni.swdesign.movienightplanner.models.ErrorModel;
import java.lang.reflect.Type;
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
public class GSONToolsTest {
    
    public GSONToolsTest() {
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
     * Test of convertJSONToObjects method, of class GSONTools.
     */
    @Test
    public void testConvertJSONToObjects_String_Class() {
        System.out.println("convertJSONToObjects");
String JSONString = "{\n" +
"  \"status_code\": 404,\n" +
"  \"success\": false,\n" +
"  \"status_message\": \"Resource not found\"\n" +
"}";
        GSONTools instance = new GSONTools();
        Object result = instance.convertJSONToObjects(JSONString, ErrorModel.class);
        ErrorModel finalError = (ErrorModel)result;
        assertEquals("Resource not found", finalError.getStatus_message());
    }
    
}
