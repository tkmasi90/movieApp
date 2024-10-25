/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

/**
 * Represents a response containing a list of streaming providers.
 * This class is typically used to deserialize responses from streaming provider
 * APIs and holds the list of {@link StreamingProvider} objects.
 * @author Make, ChatGPT(Javadoc comments)
 */
import com.google.gson.annotations.Expose;
import java.util.List;

public class StreamingResponse {
    
    @Expose
    public List<StreamingProvider> results;

    /**
     * Gets the list of streaming providers in the response.
     * 
     * @return the list of {@link StreamingProvider} objects
     */
    public List<StreamingProvider> getResults() {
        return results;
    }

    /**
     * Sets the list of streaming providers in the response.
     * 
     * @param results the list of {@link StreamingProvider} objects to set
     */
    public void setResults(List<StreamingProvider> results) {
        this.results = results;
    }
}
