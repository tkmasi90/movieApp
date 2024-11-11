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
public class GSONTools implements iGSONOperations{

    Gson gson;

    /**
     * Constructs a GSONTools instance with custom Gson settings.
     */
    public GSONTools() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param JSONString the JSON string to convert
     * @param targetClass the class of the object to convert to
     * @return the converted object
     * @throws JsonParseException if the JSON is not valid or cannot be
     * converted
     */
    @Override
    public Object convertJSONToObjects(String JSONString, Class<?> targetClass) throws JsonParseException {
        return gson.fromJson(JSONString, targetClass);
    }

    /**
     * Converts a JSON string to an object of the specified type. This method is
     * useful for converting JSON to a list of objects.
     *
     * @param <T> the type of the object to convert to
     * @param JSONString the JSON string to convert
     * @param type the type of the object to convert to
     * @return the converted object
     * @throws JsonParseException if the JSON is not valid or cannot be
     * converted
     */
    @Override
    public <T> T convertJSONToObjects(String JSONString, Type type) throws JsonParseException {
        return gson.fromJson(JSONString, type);
    }

    /**
     * Gets the Gson instance used for JSON conversion.
     *
     * @return the Gson instance
     */
    public Gson getGson() {
        return gson;
    }
}
