/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * Interface with methods to handle JSON-functionalities, e.g., serialization.
 *
 * @author janii
 */
public interface iGSONOperations {
    
    /**
    * Converts a JSON string to an object of the specified class.
    *
    * @param JSONString the JSON string to convert
    * @param targetClass the class of the object to convert to
    * @return the converted object
    * @throws JsonParseException if the JSON is not valid or cannot be
    * converted
    */
    public Object convertJSONToObjects(String JSONString, Class<?> targetClass) throws JsonParseException;
    
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
    public <T> T convertJSONToObjects(String JSONString, Type type) throws JsonParseException;
    
}
