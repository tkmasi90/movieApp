package fi.tuni.swdesign.movienightplanner;

import fi.tuni.swdesign.movienightplanner.utilities.FileDataController;
import com.google.gson.JsonIOException;
import fi.tuni.swdesign.movienightplanner.controllers.MovieDetailsController;
import fi.tuni.swdesign.movienightplanner.controllers.ProfileViewController;
import fi.tuni.swdesign.movienightplanner.controllers.RecipeViewController;
import fi.tuni.swdesign.movienightplanner.controllers.SceneController;
import fi.tuni.swdesign.movienightplanner.controllers.SearchViewController;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import javafx.scene.Parent;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {
    
    /** AppState object to manage user data and the application state */
    private AppState appState;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        
        readAppStateFromFile();
        
        // Load the SearchView and set its SceneController
        FXMLLoader searchLoader = loadFXML("SearchView");
        Parent searchView = searchLoader.load();
        SearchViewController searchViewController = searchLoader.getController();

        scene = new Scene(searchView);
        
        SceneController sceneController = new SceneController(stage, scene);
        searchViewController.setSceneController(sceneController); // Set SceneController
        
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
    
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
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