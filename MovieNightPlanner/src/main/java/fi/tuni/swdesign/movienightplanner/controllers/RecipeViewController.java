package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.App;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.fxml.FXML;

public class RecipeViewController {

    @FXML
    private void navigateToMovieDetailsView() throws IOException {
        
    App.setRoot("MovieDetailsView");
    
    }
}