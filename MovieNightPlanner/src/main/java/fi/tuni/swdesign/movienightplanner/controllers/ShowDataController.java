/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fi.tuni.swdesign.movienightplanner.models.SubtitleService;
import fi.tuni.swdesign.movienightplanner.utilities.GSONTools;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

/**
 *
 * @author janii
 */
public class ShowDataController {
    
    private final GSONTools gsonTools = new GSONTools();
    
    private final String urlPre = "https://streaming-availability.p.rapidapi.com/shows/movie/";
    private final String urlPost = "?series_granularity=show&output_language=en";

    
    // Checks if Finnish subtitles are available for a given movie from any
    // service
    public boolean areSubtitlesAvailableAtAll(int movieId){
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
       
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject)gsonTools.convertJSONToObjects(vastaus, JsonObject.class);
        
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");
        
        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");
        
        if (finnishJsonArray != null){
            return true;
        }
        
        return false;
    }
    
    
    public boolean areSubtitlesAvaiableForMovieInService(String streamingServiceName, int movieId){
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
        SubtitleService[] tempServices = null;
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject)gsonTools.convertJSONToObjects(vastaus, JsonObject.class);
        
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");
        
        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");
        
        if (finnishJsonArray != null){
            tempServices = (SubtitleService[])gsonTools.convertJSONToObjects(finnishJsonArray.toString(), SubtitleService[].class);
        }
        
        if(tempServices != null){
        for (SubtitleService s : tempServices){
            for (SubtitleService.Subtitle st : s.subtitles){
                if (st.getLocale().getLanguage().equals("fin") && s.getService().getId().equals(streamingServiceName)){
                    return true;
                }
            }
        }
        }
        
        return false;
    }
    
    public List<String> getServicesWithSubtitles(int movieId){
        
        List<String> serviceList = new ArrayList<>();
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
        SubtitleService[] tempServices = null;
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject)gsonTools.convertJSONToObjects(vastaus, JsonObject.class);
        
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");
        
        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");
        
        if (finnishJsonArray != null){
            tempServices = (SubtitleService[])gsonTools.convertJSONToObjects(finnishJsonArray.toString(), SubtitleService[].class);
        }
        
        if(tempServices != null){
        for (SubtitleService s : tempServices){
            for (SubtitleService.Subtitle st : s.subtitles){
                if (st.getLocale().getLanguage().equals("fin")){
                    serviceList.add(s.getService().getName());
                }
            }
        }
        }
        return serviceList;
    }
            
    // TODO: REFACTOR TO HTTPTOOLS
    private String makeTMOTNHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException {
        
        SimpleHttpResponse httpResponse = null;
               
        
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {

            httpClient.start();
            
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();
            // TODO: Switch to API key and login. 
            request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
            request.addHeader("accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("x-rapidapi-ua", "RapidAPI-Playground");
            request.addHeader("x-rapidapi-key", "828f0857d0msh8bd2546014cdc05p18e636jsn76036c21d578");
            request.addHeader("x-rapidapi-host", "streaming-availability.p.rapidapi.com");
            
            Future<SimpleHttpResponse> future = httpClient.execute(request, null);
            
            httpResponse = future.get();
                
        } catch (Exception e) {
            System.out.println("EX: " + e);
        }
        
        // TODO: Error handling (All relevant HTTP error codes...)
        if (httpResponse == null || httpResponse.getBodyText() == null) {
            throw new IllegalStateException("The HTTP response or the response body is null");
        }
        
        if (httpResponse.getCode() != 200)
        {
            
            
//            GSONTools gsonTools = new GSONTools();
//            
//            ErrorModel newError = (ErrorModel)gsonTools.convertJSONToObjects(httpResponse.getBodyText(), ErrorModel.class);
//            
//            throw new HttpResponseException(httpResponse.getCode(), newError.getMessage());
        }
        
        return httpResponse.getBodyText();
    
        
    }
}
