/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

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
public class CastTest {
    
    private Cast cast;
    
    public CastTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        cast = new Cast();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isAdult method, of class Cast.
     */
    @Test
    public void testIsAdult() {
        System.out.println("isAdult");
        cast.setAdult(true);
        assertTrue(cast.isAdult());

        cast.setAdult(false);
        assertFalse(cast.isAdult());
    }

    /**
     * Test of getGender method, of class Cast.
     */
    @Test
    public void testGetGender() {
        System.out.println("getGender");
        cast.setGender(1);
        assertEquals(1, cast.getGender());

        cast.setGender(2);
        assertEquals(2, cast.getGender());
    }

    /**
     * Test of getId method, of class Cast.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        cast.setId(123);
        assertEquals(123, cast.getId());
    }


    /**
     * Test of getKnownForDepartment method, of class Cast.
     */
    @Test
    public void testGetKnownForDepartment() {
        System.out.println("getKnownForDepartment");
        cast.setKnownForDepartment("Acting");
        assertEquals("Acting", cast.getKnownForDepartment());
    }

    /**
     * Test of getName method, of class Cast.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        cast.setName("John Doe");
        assertEquals("John Doe", cast.getName());
    }


    /**
     * Test of getOriginalName method, of class Cast.
     */
    @Test
    public void testGetOriginalName() {
        System.out.println("setOriginalName");
        cast.setOriginalName("Jonathan Doe");
        assertEquals("Jonathan Doe", cast.getOriginalName());
    }


    /**
     * Test of getPopularity method, of class Cast.
     */
    @Test
    public void testGetPopularity() {
        System.out.println("getPopularity");
        cast.setPopularity(9.5);
        assertEquals(9.5, cast.getPopularity(), 0.01);
    }


    /**
     * Test of getProfilePath method, of class Cast.
     */
    @Test
    public void testGetProfilePath() {
        System.out.println("getProfilePath");
        cast.setProfilePath("/path/to/profile.jpg");
        assertEquals("/path/to/profile.jpg", cast.getProfilePath());
    }


    /**
     * Test of getCastId method, of class Cast.
     */
    @Test
    public void testGetCastId() {
        System.out.println("getCastId");
        cast.setCastId(456);
        assertEquals(456, cast.getCastId());
    }


    /**
     * Test of getCharacter method, of class Cast.
     */
    @Test
    public void testGetCharacter() {
        System.out.println("getCharacter");
        cast.setCharacter("Hero");
        assertEquals("Hero", cast.getCharacter());
    }


    /**
     * Test of getCreditId method, of class Cast.
     */
    @Test
    public void testGetCreditId() {
        System.out.println("getCreditId");
        cast.setCreditId("credit123");
        assertEquals("credit123", cast.getCreditId());
    }


    /**
     * Test of getOrder method, of class Cast.
     */
    @Test
    public void testGetOrder() {
        System.out.println("getOrder");
        cast.setOrder(1);
        assertEquals(1, cast.getOrder());
    }

    
}
