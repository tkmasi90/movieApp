/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import fi.tuni.swdesign.movienightplanner.AppState;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Provides means to read state data from a file and store state data in a file.
 * 
 * @author janii
 */
public class FileDataController implements iFileOperations{
    
    private static final String STATE_FILENAME = "MovieNightPlannerState.json";
    
    
    /**
    * Reads app state data from a JSON file.
    * 
    * @param filename JSON file name
    * @return the AppState object deserialized from the file
    * @throws FileNotFoundException if the specified file is not found
    * @throws Exception if an error occurs while reading the file
    */
    @Override
    public AppState readStateFromFile(String filename) throws FileNotFoundException, Exception {
        
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .create();
        
         // Just for unit tests
        if (filename.equals("testfile.json")){
            return gson.fromJson(new FileReader("testfile.json"), AppState.class);
        }
        
       //return gson.fromJson(new FileReader(STATE_FILENAME), AppState.class);
        return gson.fromJson(new FileReader(filename), AppState.class);
        
    }
    
     /**
     * Writes state data to a file.
     * 
     * @param appState the AppState object to be serialized into a file
     * @param filename JSON file name
     * @return true if the write operation is successful
     * @throws com.google.gson.JsonIOException
     * @throws java.io.IOException
     */
    @Override
    public boolean writeStateToFile(AppState appState, String filename) throws JsonIOException, IOException {
        
        Writer appStateWriter = null;
                
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
        
        if (filename.equals("testfile.json")){
            appStateWriter = new FileWriter("testfile.json");
            
        }
        else {
            appStateWriter = new FileWriter(STATE_FILENAME);
        }
        

        try {
            gson.toJson(appState, appStateWriter);
            appStateWriter.flush();
            appStateWriter.close();
        } catch (JsonIOException | IOException e) {
            System.err.println("Error writing AppState to file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
