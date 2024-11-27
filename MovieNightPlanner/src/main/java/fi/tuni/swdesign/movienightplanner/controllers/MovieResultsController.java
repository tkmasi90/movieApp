/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;

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
    }
    
    private void setTitle(Movie movie) {
        searchName.setText(movie.getTitle());
    }
}
