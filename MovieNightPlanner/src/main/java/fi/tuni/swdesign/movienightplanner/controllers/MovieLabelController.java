/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
        
    /**
    * Sets the size of the clipping rectangle used for the movie label image.
    *
    * @param width  The desired width of the clipping rectangle.
    * @param height The desired height of the clipping rectangle.
    */
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
        movieLabelName.setText(movie.getTitle());
        movieLabelYear.setText(movie.getReleaseDate().substring(0, 4));

        // Clear existing logos
        movieLabelLogos.getChildren().clear();

        for (StreamingProvider prov : movie.getStreamingProviders()) {
            imageController.loadLogosIntoMovieLabel(prov.getLogoPath(), movieLabelLogos);
        }
    }
    
    /**
    * Updates the movie label to display a "Show More" message.
    * Includes a placeholder image and a label prompting the user to double-click.
    *
    * @param width  The width of the movie label in pixels.
    * @param height The height of the movie label in pixels.
    */
    public void addShowMore(Integer width, Integer height) {
        Label doubleClickTextLabel = new Label("Please double click me!");
        doubleClickTextLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");
        movieLabel.getChildren().clear();
        movieLabel.setPrefHeight(height);
        movieLabel.setPrefWidth(width);
        imageController.loadPosterIntoMovieLabel("showMore", movieLabelImage, height);
        StackPane.setAlignment(movieLabelImage, Pos.CENTER);
        StackPane.setAlignment(doubleClickTextLabel, Pos.BOTTOM_CENTER);
        movieLabel.getChildren().addAll(movieLabelImage, doubleClickTextLabel);
    }

    /**
     * Adds the movie image (backdrop) to the movie label.
     *
    * @param movie  The {@link Movie} object containing the backdrop path for the image.
    * @param height The desired height of the image in pixels. The width is adjusted proportionally.
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
