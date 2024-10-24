/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Model class for production country data.
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;

public class ProductionCountry {
    @Expose
    private String iso_3166_1;
    @Expose
    private String name;

    /**
 * Gets the ISO 3166-1 code.
 *
 * @return the ISO 3166-1 code
 */
public String getIso3166_1() {
    return iso_3166_1;
}

/**
 * Sets the ISO 3166-1 code.
 *
 * @param iso_3166_1 the ISO 3166-1 code to set
 */
public void setIso3166_1(String iso_3166_1) {
    this.iso_3166_1 = iso_3166_1;
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
