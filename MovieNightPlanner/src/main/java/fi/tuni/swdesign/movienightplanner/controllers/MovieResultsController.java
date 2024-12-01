/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

/**
 * Controller for managing movie search result displays. 
 * Handles the title, image, and clipping of movie items in the search results.
 * 
 * @author Make
 */
public class MovieResultsController {
    @FXML
    Label searchName;
    @FXML
    ImageView searchImg;
    
    ImageController imageController = new ImageController();
    
    /**
     * Initializes the controller. Ensures the search name label grows horizontally
     * within its container.
     */
    @FXML
    public void initialize() {
        HBox.setHgrow(searchName, Priority.ALWAYS);
    }
    
    /**
     * Sets the details for a search result item, including its image and title.
     *
     * @param movie The {@link Movie} object containing the details to display.
     */
    public void setResult(Movie movie) {
        setImage(movie, 70);
        setTitle(movie);
    }
    
    /**
     * Sets the image for the search result item, applying a clipping effect.
     *
     * @param movie  The {@link Movie} object containing the image path.
     * @param height The desired height of the image in pixels.
     */
    private void setImage(Movie movie, int height) {
        imageController.loadPosterIntoMovieLabel(movie.getBackdropPath(), searchImg, height);
        searchImg.setClip(getClip(searchImg, 0.13,0.13));
    }
    
    /**
     * Sets the title text for the search result item.
     *
     * @param movie The {@link Movie} object containing the title.
     */
    private void setTitle(Movie movie) {
        searchName.setText(movie.getTitle());
    }
    
    /**
     * Creates a clipping path for the search result image, giving it rounded corners.
     *
     * @param imageView The {@link ImageView} to apply the clipping effect to.
     * @param radiusTop The ratio of the top corner radius relative to the height of the image.
     * @param radiusBot The ratio of the bottom corner radius relative to the height of the image.
     * @return A {@link Node} containing the clipping path for the image.
     */
    private Node getClip(ImageView imageView, double radiusTop,double radiusBot) {
        Path clip;

        double height = imageView.getFitHeight();
        double width = imageView.getFitWidth();
        double radius1 = height * radiusTop;
        double radius2 = height * radiusBot;
        clip = new Path(new MoveTo(0, radius1), new ArcTo(radius1, radius1, 0, radius1, 0, false, true),
                new HLineTo(width),
                new VLineTo(height - radius2),
                new ArcTo(radius2, radius2, 0, width - radius2, height, false, true),
                new HLineTo(radius2),
                new ArcTo(radius2, radius2, 0, 0, height - radius2, false, true));


        clip.setFill(Color.ALICEBLUE);

        return clip;

    }
}
