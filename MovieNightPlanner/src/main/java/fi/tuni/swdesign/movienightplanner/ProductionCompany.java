/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

/**
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

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logo_path;
    }

    public void setLogoPath(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return origin_country;
    }

    public void setOriginCountry(String origin_country) {
        this.origin_country = origin_country;
    }
}
