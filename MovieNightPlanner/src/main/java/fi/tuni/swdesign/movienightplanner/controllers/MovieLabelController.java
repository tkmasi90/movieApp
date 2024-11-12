/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Make
 */
public class MovieLabelController {

    @FXML
    private ImageView movieLabelImage;
    @FXML
    private StackPane movieLabel;
    @FXML
    private Text movieLabelName;
    @FXML
    private Text movieLabelYear;
    @FXML
    private TilePane movieLabelLogos;
    
    @FXML
    private Rectangle clippingRectangle;
    
    public void setClipRectangleSize(double width, double height) {
        clippingRectangle.setWidth(width);
        clippingRectangle.setHeight(height);
    }

    ImageController imageController = new ImageController();

    /**
     * Adds the logos of streaming providers to the movie label.
     *
     * @param movie the movie object containing streaming provider information
     */
    public void addLogo(Movie movie) {
        movieLabelName.setText(movie.title);
        movieLabelYear.setText(movie.release_date.substring(0, 4));

        // Clear existing logos
        movieLabelLogos.getChildren().clear();

        for (StreamingProvider prov : movie.getStreamingProviders()) {
            imageController.loadLogosIntoMovieLabel(prov.getLogoPath(), movieLabelLogos);
        }
    }

    /**
     * Adds the movie image (backdrop) to the movie label.
     *
     * @param movie the movie object containing the backdrop path
     */
    public void addMovieImage(Movie movie, Integer height) {
        imageController.loadPosterIntoMovieLabel(movie.getBackdropPath(), movieLabelImage, height);
    }

    /**
     * Gets the movie label stack pane.
     *
     * @return the movie label stack pane
     */
    public StackPane getLabel() {
        return movieLabel;
    }

}
