package fi.tuni.swdesign.movienightplanner;

import fi.tuni.swdesign.movienightplanner.utilities.FileDataController;
import com.google.gson.JsonIOException;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    /** AppState object to manage user data and the application state */
    private AppState appState;

    @Override
    public void start(Stage stage) throws IOException {
        
        readAppStateFromFile();
        
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setTitle("Movie Night Planner");
        URL logoUrl = this.getClass().getResource("/images/movie_reel.jpeg");
        stage.getIcons().add(new Image(logoUrl.toString()));
        stage.show();
    }
    
    /**
    * Overrides the default stop method to hijack the application shutdown process.
    * Saves the current application state to a file before the application 
    * is stopped completely.
    */
    @Override
    public void stop() {
        writeAppStateToFile();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    private void readAppStateFromFile() {
        
        FileDataController fdc = new FileDataController();
        
        try{
            this.appState = fdc.readStateFromFile();
        } 
        catch (FileNotFoundException e){}
        catch (Exception e){}
      
    }
    
     /**
     * Writes the current application state to a file.
     * 
     * This method creates a FileDataController object and uses it to write
     * the current application state to a file.
     */
    private void writeAppStateToFile() {
       FileDataController fdc = new FileDataController();
        try {
            fdc.writeStateToFile(this.appState);
        } catch(JsonIOException | IOException e){
            System.out.println(e);
        } 
    }

}