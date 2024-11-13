/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import java.io.IOException;

import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for managing scene transitions in the application.
 * It handles switching between the search view, movie detail view, and profile view.
 * @author Make, ChatGPT(Javadoc comments)
 */
public class SceneController {
    private final Stage stage;
    private final Scene searchScene;
    private Scene movieDetailScene;
    private Scene profileScene;
    private AppState appState;
    
     /**
     * Constructs a SceneController with the given stage and initial search scene.
     *
     * @param stage The primary stage where scenes will be displayed.
     * @param scene The initial search scene to display.
     * @throws IOException if there is an issue loading the FXML resources.
     */
    public SceneController(Stage stage, Scene scene) throws IOException {
        this.stage = stage;
        searchScene = scene;

    }

    /**
     * Sets the application state, allowing access to shared data between scenes.
     *
     * @param appState The application state to set.
     */
    public void setAppState(AppState appState) {
        this.appState = appState;
    }
    
    /**
     * Switches the current scene to the search view.
     *
     * @param event The action event triggered by the user, such as a button click.
     * @throws IOException if there is an issue switching scenes.
     */
    @FXML
    public void switchToSearch(ActionEvent event) throws IOException {
        stage.setScene(searchScene);
        stage.show();
    }

    /**
     * Switches the current scene to the movie details view. If the movie details scene
     * has not been loaded previously, it initializes and configures it.
     *
     * @param event The mouse event triggered when a movie is selected.
     * @param movie The movie object whose details are to be displayed.
     * @throws IOException if there is an issue loading the movie details view.
     */

    @FXML
    public void switchToMovieDetail(MouseEvent event, Movie movie) throws IOException {
        if (movieDetailScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/tuni/swdesign/movienightplanner/MovieDetailsView.fxml"));
            Parent root = loader.load();
            MovieDetailsController movieDetailViewController = loader.getController();
            movieDetailViewController.setSceneController(this); // Set SceneController
            movieDetailViewController.setAppState(this.appState); // Set AppState
            movieDetailScene = new Scene(root);
            movieDetailScene.setUserData(movieDetailViewController);
        }

        MovieDetailsController movieDetailViewController = (MovieDetailsController) movieDetailScene.getUserData();
        movieDetailViewController.setMovie(movie);
        stage.setScene(movieDetailScene);
        stage.show();
    }
    
    /**
     * Switches the current scene to the profile view. If the profile scene has not been
     * loaded previously, it initializes and configures it.
     *
     * @param event The action event triggered by the user, such as a button click.
     * @throws IOException if there is an issue loading the profile view.
     */
    @FXML
    public void switchToProfile(ActionEvent event) throws IOException {
        if (profileScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/tuni/swdesign/movienightplanner/ProfileView.fxml"));
            Parent root = loader.load();
            ProfileViewController profileViewController = loader.getController();
            profileViewController.setSceneController(this); // Set SceneController
            profileViewController.setAppState(this.appState); // Set AppState
            profileViewController.initializeView(); // sets data in the view
            profileScene = new Scene(root);
        }

        stage.setScene(profileScene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Gets the current stage associated with the scene controller.
     *
     * @return The stage where scenes are displayed.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets the search scene.
     *
     * @return The search scene.
     */
    public Scene getSearchScene() {
        return searchScene;
    }

    /**
     * Gets the movie detail scene if it has been initialized.
     *
     * @return The movie detail scene or null if it has not been loaded yet.
     */
    public Scene getMovieDetailScene() {
        return movieDetailScene;
    }

    /**
     * Gets the profile scene if it has been initialized.
     *
     * @return The profile scene or null if it has not been loaded yet.
     */
    public Scene getProfileScene() {
        return profileScene;
    }
}
