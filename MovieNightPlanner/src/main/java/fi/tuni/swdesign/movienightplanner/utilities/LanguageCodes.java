/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author janii, Copilot
 */
public class LanguageCodes {
    private Map<String, String> languageMap;

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

    public String getLanguageCode(String key) {
        return languageMap.get(key);
    }
}

