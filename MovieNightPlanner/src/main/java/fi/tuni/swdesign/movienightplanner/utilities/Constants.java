/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * The {@code Constants} class provides a set of constant values, configurations, and utility methods
 * for managing supported languages, streaming providers, and generating URLs for movie data from The Movie Database (TMDb) API.
 * It includes predefined language pairs, streaming provider IDs, and methods to retrieve provider-related data
 * and formatted URLs for popular, top-rated, and filtered movies.
 * 
 * This class cannot be extended since it is declared as {@code final}.
 * 
 * @author Make, ChatGPT(Javadoc comments)
 */
public final class Constants {
    /** A list of supported languages with their respective language codes and names */
    private final List<Pair<String, String>> LANGUAGES;
    
    /**
     * Initializes a new instance of the {@code Constants} class, 
     * setting up the supported languages list by calling {@code createLanguageList()}.
     */
    public Constants() {
        LANGUAGES = createLanguageList();
    }

    /**
     * Creates a list of language pairs containing language codes and their corresponding names.
     * 
     * @return a list of pairs where each pair represents a language code and its name
     */
    private List<Pair<String, String>> createLanguageList() {
        List<Pair<String, String>> languagePairs = new ArrayList<>();
        
        languagePairs.add(new Pair<>("fi", "Finnish"));
        languagePairs.add(new Pair<>("en", "English"));
        languagePairs.add(new Pair<>("sv", "Swedish"));
        languagePairs.add(new Pair<>("no", "Norwegian"));
        languagePairs.add(new Pair<>("is", "Icelandic"));
        languagePairs.add(new Pair<>("dk", "Denmark"));
        languagePairs.add(new Pair<>("es", "Spanish"));
        languagePairs.add(new Pair<>("it", "Italian"));
        languagePairs.add(new Pair<>("fr", "French"));
        languagePairs.add(new Pair<>("ja", "Japanese"));
        languagePairs.add(new Pair<>("ko", "Korean"));

        return languagePairs; //
    };

    /**
     * Represents a collection of provider IDs for streaming services supported by the application.
     * Each constant field represents a unique provider identifier.
     */
    private final class PROVIDERS {
        public static final int NETFLIX = 8;
        public static final int DISNEY = 337;
        public static final int AMAZON = 119;
        public static final int VIAPLAY = 76;
        public static final int AREENA = 323;
        public static final int RUUTU = 338;
        public static final int APPLE = 2;
        public static final int SKY = 1773;
        public static final int MTV = 2029;
        public static final int MAX = 1899;
    }
    
    /**
     * Retrieves a list of streaming provider IDs by using reflection to access the static fields in the {@code PROVIDERS} class.
     * 
     * @return a list of integers representing provider IDs
     */
    private List<Integer> getProviderIds() {
        List<Integer> providerIds = new ArrayList<>();
        Field[] fields = PROVIDERS.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == int.class) {
                try {
                    providerIds.add(field.getInt(null)); // Get the value of the static field
                } catch (IllegalAccessException e) {
                    System.out.println("Unable to access field: " + field.getName());
                }
            }
        }
        return providerIds;
    }

    /**
     * Encodes the provider IDs into a URL-friendly string, where IDs are concatenated and separated by the '|' character.
     * 
     * @return a URL-encoded string representing the provider IDs, or {@code null} if encoding fails
     */
    public String getProvidersString() {
        try {
            return URLEncoder.encode((PROVIDER_IDS.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
    /**
     * Encodes a given list of provider IDs into a URL-friendly string, where IDs are concatenated and separated by the '|' character.
     * 
     * @param list the list of provider IDs to encode
     * @return a URL-encoded string representing the provider IDs, or {@code null} if encoding fails
     */
    public String getProvidersString(List<Integer> list) {
        try {
            return URLEncoder.encode((list.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
    /** A list of provider IDs initialized from the {@code PROVIDERS} class */
    public final List<Integer> PROVIDER_IDS = getProviderIds();
    
    /**
     * Retrieves the list of supported languages.
     * 
     * @return a list of pairs where each pair represents a language code and its name
     */
    public List<Pair<String, String>> getLanguages() {
        return LANGUAGES;
    }
    
    /**
     * Generates the URL for retrieving popular movies from TMDb, 
     * with the list of providers encoded as a URL parameter.
     * 
     * @return a formatted URL string for popular movies
     */
    public String getPopularMoviesUrl() {
        return String.format(MOVIE_BASE_URL, getProvidersString());
    }
    
    /**
     * Generates the URL for retrieving top-rated movies from TMDb, 
     * sorted by vote average and including only providers supported by the app.
     * 
     * @return a formatted URL string for top-rated movies
     */
    public String getTopRatedMoviesUrl() {
        return String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", getProvidersString());
    }
    
    /**
     * Retrieves the URL for fetching movie genres from TMDb.
     * 
     * @return the URL string for fetching movie genres
     */
    public String getGenresUrl() {
        return GENRES_URL;
    }
    
    
    /**
     * Generates a filtered URL for discovering movies from TMDb based on a specific list of providers.
     * 
     * @param list the list of provider IDs to include in the URL
     * @return a formatted URL string for filtered movies
     */
    public String getFilteredUrl(List<Integer> list) {
        return String.format(MOVIE_BASE_URL, getProvidersString(list));
    }
    
    /** The base URL for discovering movies from TMDb */
    private final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s";
    
    /** The URL for fetching available movie genres from TMDb */
    private final String GENRES_URL = String.format("https://api.themoviedb.org/3/genre/movie/list");
}
