/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
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
    
    ImageController imageController = new ImageController();

    public void addLogo(Movie movie) {
        movieLabelName.setText(movie.original_title);
        movieLabelYear.setText(movie.release_date.substring(0, 4));
        
        // Clear existing logos
        movieLabelLogos.getChildren().clear(); 
        
        for(StreamingProvider prov : movie.getStreamingProviders()) {  
            imageController.loadLogosIntoMovieLabel(prov.getLogoPath(), movieLabelLogos);

        }
    }
    
    public void addMovieImage(Movie movie) {
        imageController.loadPosterIntoMovieLabel(movie.getBackdropPath(), movieLabelImage);
    }
    
    public StackPane getLabel() {
        return movieLabel;
    }
}
