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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles all subtitle-related functionalities.
 *
 * @author janii
 */
public class SubtitleDataController {

    private final GSONTools gsonTools = new GSONTools();
    private final HTTPTools httpTools = new HTTPTools();

    private final String urlPre = "https://streaming-availability.p.rapidapi.com/shows/movie/";
    private final String urlPost = "?series_granularity=show&output_language=en";

    /**
     * Checks if Finnish subtitles are available for a given movie from any
     * service.
     *
     * @param movieId the ID of the movie to check for Finnish subtitles
     * @return true if Finnish subtitles are available, false otherwise
     */
    public boolean areFinnishSubtitlesAvailableAtAll(int movieId) {

        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;

        String url = urlPre + Integer.toString(movieId) + urlPost;

        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject) gsonTools.convertJSONToObjects(vastaus, JsonObject.class);

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        if (tempJsonObject == null) {
            return false;
        }
            
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");

        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");

        if (finnishJsonArray != null) {
            return true;
        }

        return false;
    }

    /**
     * Checks if Finnish subtitles are available for a given movie on a specific
     * streaming service.
     *
     * @param streamingServiceName the name of the streaming service to check
     * @param movieId the ID of the movie to check for Finnish subtitles
     * @return true if Finnish subtitles are available on the specified
     * streaming service, false otherwise
     */
    public boolean areFinnishSubtitlesAvailableForMovieInService(String streamingServiceName, int movieId) {

        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;

        SubtitleService[] tempServices = null;

        String url = urlPre + Integer.toString(movieId) + urlPost;

        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject) gsonTools.convertJSONToObjects(vastaus, JsonObject.class);

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        if (tempJsonObject == null) {
            return false;
        }

        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");

        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");

        if (finnishJsonArray != null) {
            tempServices = (SubtitleService[]) gsonTools.convertJSONToObjects(finnishJsonArray.toString(), SubtitleService[].class);
        }

        if (tempServices != null) {
            for (SubtitleService s : tempServices) {
                for (SubtitleService.Subtitle st : s.subtitles) {
                    if (st.getLocale().getLanguage().equals("fin") && s.getService().getId().equals(streamingServiceName)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Fetches the list of streaming services that provide Finnish subtitles for
     * a given movie.
     *
     * @param movieId the ID of the movie to check for Finnish subtitles
     * @return a set of streaming service names that provide Finnish subtitles
     */
    public Set<String> getServicesWithFinnishSubtitles(int movieId) {

        Set<String> serviceList = new HashSet<>();

        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;

        SubtitleService[] tempServices = null;

        String url = urlPre + Integer.toString(movieId) + urlPost;

        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject) gsonTools.convertJSONToObjects(vastaus, JsonObject.class);

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        if (tempJsonObject == null) {
            return serviceList;
        }

        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");

        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");

        if (finnishJsonArray != null) {
            tempServices = (SubtitleService[]) gsonTools.convertJSONToObjects(finnishJsonArray.toString(), SubtitleService[].class);
        }

        if (tempServices != null) {
            for (SubtitleService s : tempServices) {
                for (SubtitleService.Subtitle st : s.subtitles) {
                    if (st.getLocale().getLanguage().equals("fin")) {
                        serviceList.add(s.getService().getName());
                    }
                }
            }
        }
        return serviceList;
    }

    /**
     * Checks if Finnish area is supported for a given movie.
     *
     * @param movieId the ID of the movie to check for Finnish subtitle support
     * @return true if Finnish subtitles are supported, false otherwise
     */
    public boolean isFinnishAreaSupported(int movieId) {

        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;

        String url = urlPre + Integer.toString(movieId) + urlPost;

        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject) gsonTools.convertJSONToObjects(vastaus, JsonObject.class);

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        if (tempJsonObject == null) {
            return false;
        }
                
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");

        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");

        if (finnishJsonArray == null) {
            return false;
        }

        return true;
    }

    /**
     * Checks for the availability of required subtitles for a given movie on a
     * specific streaming service.
     *
     * @param movieId the ID of the movie to check for subtitles
     * @param streamingServiceName the name of the streaming service to check
     * @param languageList the list of languages to check for subtitles
     * @return a list of languages for which subtitles are available on the
     * specified streaming service
     */
    public List<String> checkRequiredSubtitlesForMovieInService(int movieId, String streamingServiceName, List<String> languageList) {

        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;
        JsonArray finnishJsonArray = null;

        SubtitleService[] tempServices = null;

        List<String> subtitleList = new ArrayList<>();

        String url = urlPre + Integer.toString(movieId) + urlPost;

        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject) gsonTools.convertJSONToObjects(vastaus, JsonObject.class);

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        if (tempJsonObject == null) {
            return subtitleList;
        }

        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");
        finnishJsonArray = streamingOptionsJsonObject.getAsJsonArray("fi");

        for (String language : languageList) {

            if (finnishJsonArray != null) {
                tempServices = (SubtitleService[]) gsonTools.convertJSONToObjects(finnishJsonArray.toString(), SubtitleService[].class);

                if (tempServices != null) {
                    for (SubtitleService s : tempServices) {
                        for (SubtitleService.Subtitle st : s.subtitles) {
                            if (st.getLocale().getLanguage().equals(LanguageCodes.getIsoCodeFromCc(language)) && s.getService().getId().equals(streamingServiceName)) {
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
    
    /**
     * Checks if subtitles are available for a given movie in a specific language.
     *
     * @param movieId the ID of the movie to check for subtitles
     * @param language the language code (e.g., "en" for English, "fi" for Finnish) to check for subtitles
     * @return {@code true} if subtitles in the specified language are available for the movie, {@code false} otherwise
     */
    public boolean areSubtitlesAvailable(int movieId, String language) {

        JsonObject tempJsonObject = null;
        JsonObject streamingOptionsJsonObject = null;

        String url = urlPre + Integer.toString(movieId) + urlPost;

        try {
            String vastaus = httpTools.makeTMOTNHttpRequest(url);
            tempJsonObject = (JsonObject) gsonTools.convertJSONToObjects(vastaus, JsonObject.class);

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        
        if (tempJsonObject == null) {
            return false;
        }
                
        streamingOptionsJsonObject = tempJsonObject.getAsJsonObject("streamingOptions");

        JsonArray jsonArray = streamingOptionsJsonObject.getAsJsonArray(language);

        return jsonArray != null;
    };
}
