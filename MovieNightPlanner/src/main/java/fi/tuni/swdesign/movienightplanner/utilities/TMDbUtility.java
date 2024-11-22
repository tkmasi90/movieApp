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
public final class TMDbUtility {

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
    public String getFiltersInt(List<Integer> list) {
        try {
            return URLEncoder.encode((list.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
        /**
     * Encodes a given list of genres and languages into a URL-friendly string, where strings are concatenated and separated by the '|' character.
     * 
     * @param list the list of genres or languages to encode
     * @return a URL-encoded string representing the genres or languages, or {@code null} if encoding fails
     */
    public String getFiltersString(List<String> list) {
                try {
            return URLEncoder.encode((list.stream()
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
    /** A list of provider IDs initialized from the {@code PROVIDERS} class */
    public final List<Integer> PROVIDER_IDS = getProviderIds();
    
    
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
     * Retrieves the URL for fetching images from TMDb.
     * 
     * @return the URL string for fetching images
     */
    public String getImagesBaseUrl() {
        return TMDB_IMAGE_BASE_URL;
    }
    
    /**
     * Generates a formatted URL for discovering movies from TMDb based on specified filtering criteria.
     * The URL incorporates filters for genres, audio languages, streaming providers, and minimum rating.
     *
     * @param genreList A list of genre IDs to filter movies by.
     * @param audioList A list of audio language codes to filter movies by.
     * @param provList A list of streaming provider IDs to include in the filter.
     * @param minRating The minimum rating to include in the filter.
     * @return A formatted URL string with the applied filters, ready for use in TMDb API requests.
     */
    public String getFilteredUrl(List<Integer> genreList, List<String> audioList, List<Integer> provList, String minRating) {
        return String.format(MOVIE_BASE_URL_FILTER, getFiltersInt(genreList), getFiltersString(audioList), getFiltersInt(provList), minRating);
    }
    
    /** The base URL for discovering movies from TMDb */
    private final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/discover/movie?api_key=6d02f7885467716ba92cf8c3c4651678&include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate%%7Cfree&with_watch_providers=%s";
    
    /** The base URL for discovering movies from TMDb using filters */
    private final String MOVIE_BASE_URL_FILTER= "https://api.themoviedb.org/3/discover/movie?api_key=6d02f7885467716ba92cf8c3c4651678&include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_genres=%s&with_original_language=%s&with_watch_monetization_types=flatrate%%7Cfree&with_watch_providers=%s&vote_average.gte=%s&vote_count.gte=200";

    
    /** The URL for fetching available movie genres from TMDb */
    private final String GENRES_URL = "https://api.themoviedb.org/3/genre/movie/list";
    
    /** The URL for fetching images from TMDb */
    private final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
}
