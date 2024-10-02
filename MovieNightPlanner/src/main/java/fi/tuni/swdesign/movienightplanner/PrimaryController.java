package fi.tuni.swdesign.movienightplanner;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController {
    
    @FXML VBox popularMoviesVBox;
    @FXML VBox topRatedMoviesVBox;
    
    @FXML
    public void initialize(){
        MovieDataController mdc = new MovieDataController();
        
        PopularMoviesResponse popularMoviesResponse = mdc.getPopularMovies();
        TopRatedMoviesResponse topRatedMoviesResponse = mdc.getTopRatedMovies();
        
        if (popularMoviesResponse != null) {
            List<Movie> tempMovieList = popularMoviesResponse.getResults();
            for (Movie m : tempMovieList) {
                HBox tempHBox = new HBox();
                Label movieName = new Label();
                movieName.setText(m.getTitle());
                tempHBox.getChildren().add(movieName);
                popularMoviesVBox.getChildren().add(tempHBox);
            }
        } else {
            System.out.println("Failed to fetch popular movies.");
        }

        if (topRatedMoviesResponse != null) {
            List<Movie> tempTopRatedMovieList = topRatedMoviesResponse.getResults();
            for (Movie m : tempTopRatedMovieList) {
                HBox tempHBox = new HBox();
                Label movieName = new Label();
                movieName.setText(m.getTitle());
                tempHBox.getChildren().add(movieName);
                topRatedMoviesVBox.getChildren().add(tempHBox);
            }
        } else {
            System.out.println("Failed to fetch top-rated movies.");
        }
       
    }
    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
