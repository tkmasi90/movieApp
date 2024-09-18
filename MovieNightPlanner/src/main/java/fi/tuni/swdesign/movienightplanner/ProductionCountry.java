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

public class ProductionCountry {
    @Expose
    private String iso_3166_1;
    @Expose
    private String name;

    // Getters and Setters

    public String getIso3166_1() {
        return iso_3166_1;
    }

    public void setIso3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
