/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 *
 * @author Make
 */
import com.google.gson.annotations.Expose;

public class StreamingProvider {

    @Expose
    public String provider_name;

    @Expose
    public int provider_id;

    @Expose
    public String logo_path;

    // Constructor
    public StreamingProvider() {}

    // Getters and Setters
    public String getProviderName() {
        return provider_name;
    }

    public void setProviderName(String provider_name) {
        this.provider_name = provider_name;
    }

    public int getProviderId() {
        return provider_id;
    }

    public void setProviderId(int provider_id) {
        this.provider_id = provider_id;
    }

    public String getLogoPath() {
        return logo_path;
    }

    public void setLogoPath(String logo_path) {
        this.logo_path = logo_path;
    }
}

