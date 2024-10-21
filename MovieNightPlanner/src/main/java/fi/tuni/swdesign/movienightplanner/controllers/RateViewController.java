package fi.tuni.swdesign.movienightplanner.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class RateViewController {

    @FXML
    private Rating rating;

    @FXML
    private Button rateButton;

    @FXML
    private Button cancelButton;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
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
        double ratingValue = rating.getRating();
        // Handle the rating value (e.g., save it to the database or update the model)
        System.out.println("Rated: " + ratingValue);
        stage.close();
    }

    @FXML
    private void handleCancelButtonAction() {
        stage.close();
    }
}