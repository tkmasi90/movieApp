/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 *
 * @author janii, Copilot
 */
import com.google.gson.annotations.Expose;

public class SpokenLanguage {
    @Expose
    private String english_name;
    @Expose
    private String iso_639_1;
    @Expose
    private String name;

    // Getters and Setters

    public String getEnglishName() {
        return english_name;
    }

    public void setEnglishName(String english_name) {
        this.english_name = english_name;
    }

    public String getIso639_1() {
        return iso_639_1;
    }

    public void setIso639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
