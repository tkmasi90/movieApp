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
import fi.tuni.swdesign.movienightplanner.utilities.LanguageCodes;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final HTTPTools httpTools = new HTTPTools();
    
    private final String urlPre = "https://streaming-availability.p.rapidapi.com/shows/movie/";
    private final String urlPost = "?series_granularity=show&output_language=en";

    
    // Checks if Finnish subtitles are available for a given movie from any
    // service
    public boolean areFinnishSubtitlesAvailableAtAll(int movieId){
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
       
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
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
    
    public boolean areFinnishSubtitlesAvailableForMovieInService(String streamingServiceName, int movieId){
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
        SubtitleService[] tempServices = null;
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
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
    
    public Set<String> getServicesWithFinnishSubtitles(int movieId){
        
        Set<String> serviceList = new HashSet<>();
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
        SubtitleService[] tempServices = null;
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
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

    public boolean isFinnishAreaSupported(int movieId){
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject)gsonTools.convertJSONToObjects(vastaus, JsonObject.class);
        
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");
            
           finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");
          
           if (finnishJsonArray == null){
               return false;
           }
        
        return true;
    }
    
        public List<String> checkRequiredSubtitlesForMovieInService(int movieId, String streamingServiceName, List<String> languageList){
        
        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;
        
        SubtitleService[] tempServices = null;

        LanguageCodes languageCodes = new LanguageCodes();
        
        List<String> subtitleList = new ArrayList<>();
        
        String url = urlPre + Integer.toString(movieId) + urlPost;
        
        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject)gsonTools.convertJSONToObjects(vastaus, JsonObject.class);
        
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");
        
        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");
        
        for (String language : languageList){
 
           if (finnishJsonArray != null){
               tempServices = (SubtitleService[])gsonTools.convertJSONToObjects(finnishJsonArray.toString(), SubtitleService[].class);
               
                       if(tempServices != null){
                             for (SubtitleService s : tempServices){
                                for (SubtitleService.Subtitle st : s.subtitles){
                                    if (st.getLocale().getLanguage().equals(languageCodes.getLanguageCode(language)) && s.getService().getId().equals(streamingServiceName)){
                                        subtitleList.add(language);
                                    }
                                }
                            }
                        }           
           } else {
                continue;
           }
        }
        
        return subtitleList;
    }
    
}
