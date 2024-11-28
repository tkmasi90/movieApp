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
 *
 * @author Make
 */
public class MovieResultsController {
    @FXML
    Label searchName;
    @FXML
    ImageView searchImg;
    
    ImageController imageController = new ImageController();
    
    @FXML
    public void initialize() {
        HBox.setHgrow(searchName, Priority.ALWAYS);
    }
    
    public void setResult(Movie movie) {
        setImage(movie, 70);
        setTitle(movie);
    }
    
    private void setImage(Movie movie, int height) {
        imageController.loadPosterIntoMovieLabel(movie.getBackdropPath(), searchImg, height);
        searchImg.setClip(getClip(searchImg, 0.13,0.13));
    }
    
    private void setTitle(Movie movie) {
        searchName.setText(movie.getTitle());
    }
    
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
