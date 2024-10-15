/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * A helper class to facilitate all JSON-GSON-POJO-related operations.
 * 
 * @author janii, Markus
 */
public class GSONTools {
    Gson gson;
    
    public GSONTools() {
        this.gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();     
    }
    
    /**
    * Handles HTTP requests and responses asynchronously.
    *
    * @param JSONString  The JSON string to be deserialized.
    * @param targetClass  The class of the object to convert the JSON into.
    * @return Object      The object created based on the JSON string.
    * @throws JsonParseException    Parsing failed.
    * 
    * @author janii
    */
    public Object convertJSONToObjects(String JSONString, Class<?> targetClass) throws JsonParseException{
        return gson.fromJson(JSONString, targetClass);
    }
    
    // For a list of objects (use Type for lists)
    public <T> T convertJSONToObjects(String JSONString, Type type) throws JsonParseException {
        return gson.fromJson(JSONString, type);
    }
    
    public Gson getGson() {
        return gson;
    }
}
