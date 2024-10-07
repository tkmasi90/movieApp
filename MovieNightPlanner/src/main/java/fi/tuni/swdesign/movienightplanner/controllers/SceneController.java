/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    public SceneController(Stage stage, Scene scene) throws IOException {
        this.stage = stage;
        searchScene = scene;

    }
    
    @FXML
    public void switchToSearch(ActionEvent event) throws IOException {
        stage.setScene(searchScene);
        stage.show();
    }

    @FXML
    public void switchToMovieDetail(ActionEvent event) throws IOException {
        if (movieDetailScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/tuni/swdesign/movienightplanner/MovieDetailsView.fxml"));
            Parent root = loader.load();
            MovieDetailsController movieDetailViewController = loader.getController();
            movieDetailViewController.setSceneController(this); // Set SceneController
            movieDetailScene = new Scene(root);
        }

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
        stage.show();
    }
}
