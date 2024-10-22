// src/main/java/fi/tuni/swdesign/movienightplanner/controllers/RateViewController.java
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class RateViewController {

    @FXML private Rating rating;
    @FXML private Button rateButton;
    @FXML private Button cancelButton;

    private Stage stage;
    private AppState appState;
    private int movieId;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;

        // Load the existing rating if available
        int existingRating = appState.getMovieRating(movieId);
        if (existingRating != -1) {
            rating.setRating(existingRating);
        }
    }

    @FXML
    private void initialize() {
        // Disable the rate button if the rating is 0
        rateButton.setDisable(rating.getRating() == 0);
        // Add a listener to the rating control to enable/disable the rate button
        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            rateButton.setDisable(newValue.doubleValue() == 0);
        });
    }

    @FXML
    private void handleRateButtonAction() {
        int ratingValue = (int) rating.getRating();
        // Save the rating to AppState
        appState.saveMovieRating(movieId, ratingValue);
        stage.close();
    }

    @FXML
    private void handleCancelButtonAction() {
        stage.close();
    }
}