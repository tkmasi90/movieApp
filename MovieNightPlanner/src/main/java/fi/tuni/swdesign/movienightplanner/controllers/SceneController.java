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
 *
 * @author Make
 */
public class SceneController {
    private final Stage stage;
    private final Scene searchScene;
    private Scene movieDetailScene;
    private Scene profileScene;
    private AppState appState;
    
    public SceneController(Stage stage, Scene scene) throws IOException {
        this.stage = stage;
        searchScene = scene;

    }

    public void setAppState(AppState appState) {
        this.appState = appState;
    }
    
    @FXML
    public void switchToSearch(ActionEvent event) throws IOException {
        stage.setScene(searchScene);
        stage.show();
    }

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
    
    @FXML
    public void switchToProfile(ActionEvent event) throws IOException {
        if (profileScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/tuni/swdesign/movienightplanner/ProfileView.fxml"));
            Parent root = loader.load();
            ProfileViewController profileViewController = loader.getController();
            profileViewController.setSceneController(this); // Set SceneController
            profileScene = new Scene(root);
        }

        stage.setScene(profileScene);
        stage.sizeToScene();
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getSearchScene() {
        return searchScene;
    }

    public Scene getMovieDetailScene() {
        return movieDetailScene;
    }

    public Scene getProfileScene() {
        return profileScene;
    }
}
