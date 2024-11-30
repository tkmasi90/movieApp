/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

/**
 * Model class for a movie genre.
 *
 * @author janii, Copilot
 */
public class Genre {

    /**
     * Unique identifier for the genre.
     */
    @Expose
    private int id;
    
    /**
     * Name of the genre.
     */
    @Expose
    private String name;

    /**
     * Gets the ID.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
