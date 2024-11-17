/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for language code management with subtitles.
 *
 * @author janii, Markus, Copilot
 */
public class LanguageCodes {
   
    private static final Map<String, Pair<String, String>> LANGUAGES;
    
    /**
     * Constructs a LanguageCodes instance and initializes the language map with
     * predefined language codes.
     */
    static {
        Map<String, Pair<String, String>> languageMap = new LinkedHashMap<>();
        
        languageMap.put("fi", new Pair<>("fin", "Finnish"));
        languageMap.put("en", new Pair<>("eng", "English"));
        languageMap.put("no", new Pair<>("nob", "Norwegian"));
        languageMap.put("sv", new Pair<>("swe", "Swedish"));
        languageMap.put("da", new Pair<>("dan", "Danish"));
        languageMap.put("is", new Pair<>("isl", "Icelandic"));
        languageMap.put("de", new Pair<>("deu", "German"));
        languageMap.put("es", new Pair<>("spa", "Spanish"));
        languageMap.put("it", new Pair<>("ita", "Italian"));
        languageMap.put("fr", new Pair<>("fra", "French"));
        languageMap.put("ko", new Pair<>("kor", "Korean"));
        languageMap.put("ja", new Pair<>("jpn", "Japanese"));
        
        LANGUAGES = Collections.unmodifiableMap(languageMap);
    }

    /**
     * Gets the language code for the specified key.
     *
     * @param cc the key for which to get the language code
     * @return the language code corresponding to the key, or null if the key is
     * not found
     */
    public static String getIsoCodeFromCc(String cc) {
        return LANGUAGES.get(cc).getIsoCode();
    }
    
     /**
     * Gets the country code from a language name.
     *
     * @param name the language name
     * @return the country code corresponding to the language name, or null if not found
     */
    public static String getCountryCodeFromName(String name) {
        for (Map.Entry<String, Pair<String, String>> entry : LANGUAGES.entrySet()) {
            if (entry.getValue().getLanguageName().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets the country code from a country code key.
     *
     * @param name the key to lookup
     * @return the same country code key, or null if not found
     */
    public static String getCcFromName(String name) {
        return LANGUAGES.containsKey(name) ? name : null;
    }
    
    public static String getIsoCodeFromName(String name) {
        for (Map.Entry<String, Pair<String, String>> entry : LANGUAGES.entrySet()) {
            if (entry.getValue().getLanguageName().equals(name)) {
                return entry.getValue().getIsoCode();
            }
        }
        return null;
    }

    /**
     * Gets a list of all country codes.
     *
     * @return a list of all country codes
     */
    public static List<String> getAllCountryCodes() {
        return new ArrayList<>(LANGUAGES.keySet());
    }

    /**
     * Gets a list of all ISO codes.
     *
     * @return a list of all ISO codes
     */
    public static List<String> getAllIsoCodes() {
        List<String> isoCodes = new ArrayList<>();
        for (Pair<String, String> pair : LANGUAGES.values()) {
            isoCodes.add(pair.getIsoCode());
        }
        return isoCodes;
    }

    /**
     * Gets a list of all language names.
     *
     * @return a list of all language names
     */
    public static List<String> getAllLanguageNames() {
        List<String> languageNames = new ArrayList<>();
        for (Pair<String, String> pair : LANGUAGES.values()) {
            languageNames.add(pair.getLanguageName());
        }
        return languageNames;
    }
    
    public static Integer getLanguageListLength() {
        return LANGUAGES.size();
    }

    private static class Pair<T, U> {
        private final T isoCode;
        private final U languageName;

        public Pair(T isoCode, U languageName) {
            this.isoCode = isoCode;
            this.languageName = languageName;
        }

        public T getIsoCode() {
            return isoCode;
        }

        public U getLanguageName() {
            return languageName;
        }

        @Override
        public String toString() {
            return isoCode + " - " + languageName;
        }
    }
}

