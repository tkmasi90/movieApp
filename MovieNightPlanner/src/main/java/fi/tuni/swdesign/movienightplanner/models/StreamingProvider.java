/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Represents a streaming provider, containing its name, ID, and logo path.
 * This class is used to encapsulate the details of a streaming provider 
 * and is primarily used in conjunction with streaming services data.
 * @author Make, ChatGPT & Copilot(Javadoc comments)
 */
import com.google.gson.annotations.Expose;

public class StreamingProvider {

    /**
     * The name of the streaming provider.
     */
    @Expose
    private String provider_name;
    
    /**
     * Unique identifier for the streaming provider.
     */
    @Expose
    private int provider_id;
    
    /**
     * Path to the logo image of the streaming provider.
     */
    @Expose
    private String logo_path;

    /**
     * Gets the name of the streaming provider.
     * 
     * @return the provider name
     */
    public String getProviderName() {
        return provider_name;
    }
    
    /**
     * Sets the name of the streaming provider.
     * 
     * @param provider_name the name to set for the provider
     */
    public void setProviderName(String provider_name) {
        this.provider_name = provider_name;
    }

    /**
     * Gets the unique ID of the streaming provider.
     * 
     * @return the provider ID
     */
    public int getProviderId() {
        return provider_id;
    }

    /**
     * Sets the unique ID of the streaming provider.
     * 
     * @param provider_id the ID to set for the provider
     */
    public void setProviderId(int provider_id) {
        this.provider_id = provider_id;
    }

    /**
     * Gets the path or URL of the streaming provider's logo.
     * 
     * @return the logo path or URL
     */
    public String getLogoPath() {
        return logo_path;
    }

    /**
     * Sets the path or URL of the streaming provider's logo.
     * 
     * @param logo_path the path or URL to set for the logo
     */
    public void setLogoPath(String logo_path) {
        this.logo_path = logo_path;
    }
}

