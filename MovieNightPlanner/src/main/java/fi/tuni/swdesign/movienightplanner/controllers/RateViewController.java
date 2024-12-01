package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * Controller for the rate view.
 *
 * @author kian, Copilot
 */
public class RateViewController {

    @FXML private Rating rating;
    @FXML private Button rateButton;
    @FXML private Button cancelButton;

    private Stage stage;
    private AppState appState;
    private Movie movie;

    /**
     * Sets the stage for this controller.
     *
     * @param stage the Stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the AppState for this controller.
     *
     * @param appState the AppState to set
     */
    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    /**
     * Sets the movie and loads the existing rating if available.
     *
     * @param movie the ID of the movie to set
     */
    public void setMovie(Movie movie) {
        this.movie = movie;

        // Load the existing rating if available
        int existingRating = appState.getMovieRating(movie);
        if (existingRating != -1) {
            rating.setRating(existingRating);
        }
    }

    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        // Disable the rate button if the rating is 0
        rateButton.setDisable(rating.getRating() == 0);
        // Add a listener to the rating control to enable/disable the rate button
        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            rateButton.setDisable(newValue.doubleValue() == 0);
        });
    }

    /**
     * Handles the action of the rate button.
     */
    @FXML
    private void handleRateButtonAction() {
        int ratingValue = (int) rating.getRating();
        // Save the rating to AppState
        appState.saveMovieRating(movie, ratingValue);
        stage.close();
    }

    /**
     * Handles the action of the cancel button.
     */
    @FXML
    private void handleCancelButtonAction() {
        stage.close();
    }
}