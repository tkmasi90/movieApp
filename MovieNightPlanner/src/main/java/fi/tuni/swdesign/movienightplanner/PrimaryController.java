package fi.tuni.swdesign.movienightplanner;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController {
    
    @FXML VBox popularMoviesVBox;
    
    @FXML
    public void initialize(){
        MovieDataController mdc = new MovieDataController();
        
        List<Movie> tempMovieList = mdc.getPopularMovies().getResults();
        
        for (Movie m : tempMovieList){
            
            HBox tempHBox = new HBox();
            
            Label movieName = new Label();
            
            movieName.setText(m.getTitle());
            
            tempHBox.getChildren().add(movieName);
            
            popularMoviesVBox.getChildren().add(tempHBox);

        }
    }
    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
