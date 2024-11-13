/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.JsonIOException;
import fi.tuni.swdesign.movienightplanner.AppState;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface with operations to write and read data from the disk.
 *
 * @author janii
 */
public interface iFileOperations {
    
    /**
    * Reads app state data from a JSON file.
    * 
    * @return the AppState object deserialized from the file
    * @throws FileNotFoundException if the specified file is not found
    * @throws Exception if an error occurs while reading the file
    */
    public AppState readStateFromFile() throws FileNotFoundException, Exception;
    
    /**
    * Writes state data to a file.
    * 
    * @param appState the AppState object to be serialized into a file
    * @return true if the write operation is successful
    * @throws com.google.gson.JsonIOException
    * @throws java.io.IOException
    */
    public boolean writeStateToFile(AppState appState) throws JsonIOException, IOException;
     
}
