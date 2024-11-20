package fi.tuni.swdesign.movienightplanner;

import fi.tuni.swdesign.movienightplanner.utilities.FileDataController;
import com.google.gson.JsonIOException;
import fi.tuni.swdesign.movienightplanner.controllers.MovieDetailsController;
import fi.tuni.swdesign.movienightplanner.controllers.ProfileViewController;
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

    @Override
    public void start(Stage stage) throws IOException {
        readAppStateFromFile();
        
        // Add title and logo for app
        stage.setTitle("Movie Night Planner");
        URL logoUrl = this.getClass().getResource("/images/movie_reel.jpeg");
        stage.getIcons().add(new Image(logoUrl.toString()));

        // Load the SearchView and set its SceneController
        FXMLLoader searchLoader = loadFXML("SearchView");
        Parent searchView = searchLoader.load();
        
        // Load the MovieDetailsView and set its SceneController
        FXMLLoader detailsLoader = loadFXML("MovieDetailsView");
        Parent detailsView = detailsLoader.load();
        
        // Load the ProfileView and set its SceneController
        FXMLLoader profileLoader = loadFXML("ProfileView");
        Parent profileView = profileLoader.load();

        // Create Scenes
        Scene searchScene = new Scene(searchView);
        Scene detailsScene = new Scene(detailsView);
        Scene profileScene = new Scene(profileView);
        
        stage.setScene(searchScene);
        stage.sizeToScene();
        stage.setResizable(false);

        // Set scenes for SceneController
        SceneController sceneController = new SceneController(stage, searchScene, profileScene, detailsScene);
        
        // Set Search View
        SearchViewController searchViewController = searchLoader.getController();
        searchViewController.setSceneController(sceneController);
        searchViewController.setAppState(this.appState);
        searchViewController.updateFilters();
        
        // Set Profile View
        ProfileViewController profileViewController = profileLoader.getController();
        profileViewController.setSceneController(sceneController);
        profileViewController.setSearchViewController(searchViewController);
        profileViewController.setAppState(this.appState);
        profileScene.setUserData(profileViewController);
        profileViewController.updateData();
        profileViewController.setFiltersFromState();
        
        // Set Movie Detail View
        MovieDetailsController movieDetailsController = detailsLoader.getController();
        movieDetailsController.setSceneController(sceneController);
        movieDetailsController.setAppState(this.appState);
        detailsScene.setUserData(movieDetailsController);
        
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
            this.appState = fdc.readStateFromFile("MovieNightPlannerState.json");
            if(this.appState == null){
                this.appState = new AppState();
            }
        } 
        catch (FileNotFoundException e){
            this.appState = new AppState();
        }
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
            fdc.writeStateToFile(this.appState,"MovieNightPlannerState.json");
        } catch(JsonIOException | IOException e){
            System.out.println(e);
        } 
    }
}