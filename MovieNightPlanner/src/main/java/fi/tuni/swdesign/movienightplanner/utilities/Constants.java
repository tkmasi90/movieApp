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
 *
 * @author Make
 */
public final class Constants {
    private final List<Pair<String, String>> LANGUAGES;
    
    public Constants() {
        LANGUAGES = createLanguageList();
    }

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

    // List of providers that the app supports at the moment
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
    // Method to get provider IDs dynamically using reflection
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

    public String getProvidersString() {
        try {
            return URLEncoder.encode((PROVIDER_IDS.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
        public String getProvidersString(List<Integer> list) {
        try {
            return URLEncoder.encode((list.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
    public final List<Integer> PROVIDER_IDS = getProviderIds();
    
    public List<Pair<String, String>> getLanguages() {
        return LANGUAGES;
    }
    
    public String getPopularMoviesUrl() {
        return String.format(MOVIE_BASE_URL, getProvidersString());
    }
    
    public String getTopRatedMoviesUrl() {
        return String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", getProvidersString());
    }
    
    public String getGenresUrl() {
        return GENRES_URL;
    }
    
    // TODO: add genre and spoken language filter list
    public String getFilteredUrl(List<Integer> list) {
        return String.format(MOVIE_BASE_URL, getProvidersString(list));
    }
    
    private final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s";
    private final String GENRES_URL = String.format("https://api.themoviedb.org/3/genre/movie/list");
}
