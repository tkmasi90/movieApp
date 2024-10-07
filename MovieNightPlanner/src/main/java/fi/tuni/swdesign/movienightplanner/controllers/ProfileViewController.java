package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.App;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfileViewController {
    private SceneController sceneController;
    
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private void navigateToSearchView(ActionEvent event) {
        System.out.println("Button clicked, navigating to Search View...");
        try {
            sceneController.switchToSearch(event); // Delegate scene switching to SceneController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}