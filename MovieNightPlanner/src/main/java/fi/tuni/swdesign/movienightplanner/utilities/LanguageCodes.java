/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for language code management with subtitles.
 *
 * @author janii, Copilot
 */
public class LanguageCodes {

    private Map<String, String> languageMap;

    /**
     * Constructs a LanguageCodes instance and initializes the language map with
     * predefined language codes.
     */
    public LanguageCodes() {
        languageMap = new HashMap<>();
        languageMap.put("fi", "fin");
        languageMap.put("en", "eng");
        languageMap.put("no", "nob");
        languageMap.put("sv", "swe");
        languageMap.put("is", "isl");
        languageMap.put("de", "deu");
        languageMap.put("es", "spa");
        languageMap.put("it", "ita");
        languageMap.put("fr", "fra");
        languageMap.put("ko", "kor");
    }

    /**
     * Gets the language code for the specified key.
     *
     * @param key the key for which to get the language code
     * @return the language code corresponding to the key, or null if the key is
     * not found
     */
    public String getLanguageCode(String key) {
        return languageMap.get(key);
    }
}
