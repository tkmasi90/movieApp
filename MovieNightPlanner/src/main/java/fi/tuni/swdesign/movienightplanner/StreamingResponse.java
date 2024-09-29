/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

/**
 *
 * @author Make
 */
import com.google.gson.annotations.Expose;
import java.util.List;

public class StreamingResponse {
    
    @Expose
    public List<StreamingProvider> results;

    public List<StreamingProvider> getResults() {
        return results;
    }

    public void setResults(List<StreamingProvider> results) {
        this.results = results;
    }
}
