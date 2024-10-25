/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Model class for production company data.
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;

public class ProductionCompany {

    @Expose
    private int id;
    @Expose
    private String logo_path;
    @Expose
    private String name;
    @Expose
    private String origin_country;

    /**
     * Gets the ID of the streaming provider.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the streaming provider.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the logo path of the streaming provider.
     *
     * @return the logo path
     */
    public String getLogoPath() {
        return logo_path;
    }

    /**
     * Sets the logo path of the streaming provider.
     *
     * @param logo_path the logo path to set
     */
    public void setLogoPath(String logo_path) {
        this.logo_path = logo_path;
    }

    /**
     * Gets the name of the streaming provider.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the streaming provider.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the origin country of the streaming provider.
     *
     * @return the origin country
     */
    public String getOriginCountry() {
        return origin_country;
    }

    /**
     * Sets the origin country of the streaming provider.
     *
     * @param origin_country the origin country to set
     */
    public void setOriginCountry(String origin_country) {
        this.origin_country = origin_country;
    }

}
