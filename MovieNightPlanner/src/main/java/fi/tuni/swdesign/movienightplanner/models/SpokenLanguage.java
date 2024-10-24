/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Model class for spoken language in a movie.
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;

public class SpokenLanguage {

    @Expose
    public String english_name;
    @Expose
    public String iso_639_1;
    @Expose
    public String name;

    /**
     * Gets the English name.
     *
     * @return the English name
     */
    public String getEnglishName() {
        return english_name;
    }

    /**
     * Sets the English name.
     *
     * @param english_name the new English name
     */
    public void setEnglishName(String english_name) {
        this.english_name = english_name;
    }

    /**
     * Gets the ISO 639-1 code.
     *
     * @return the ISO 639-1 code
     */
    public String getIso639_1() {
        return iso_639_1;
    }

    /**
     * Sets the ISO 639-1 code.
     *
     * @param iso_639_1 the new ISO 639-1 code
     */
    public void setIso639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
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
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

}
