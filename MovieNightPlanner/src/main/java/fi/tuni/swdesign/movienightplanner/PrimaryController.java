package fi.tuni.swdesign.movienightplanner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController {
    
    @FXML VBox popularMoviesVBox;
    @FXML VBox topRatedMoviesVBox;

    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies");
    
    @FXML
    public void initialize(){
        
        // Add "Loading" labels to the VBox initially
        popularMoviesVBox.getChildren().add(popularMoviesLoadingLabel);
        topRatedMoviesVBox.getChildren().add(topRatedMoviesLoadingLabel);
        
        MovieDataController mdc = new MovieDataController();
        
        // TODO:
        // This function doesn't need be called each time.
        // We can fetch this info only on first time and save to a local json
        if(mdc.getStreamProviderMap() == null)
            mdc.fetchStreamingProviders();
        else {
            // TODO
        }
        
        // Fetch Popular Movies Asynchronously
        fetchMoviesAsync(mdc, "popular");

        // Fetch Top-Rated Movies Asynchronously
        fetchMoviesAsync(mdc, "top");
       
    }
    
    // Helper function to get the streaming services as a text string
    private String getStreamingServicesText(Movie movie) {
        StringBuilder sTxt = new StringBuilder("  - ");
        for (StreamingProvider stream : movie.getStreamingProviders()) {
            sTxt.append(stream.getProviderName()).append(" ");
        }
        return sTxt.toString();
    }
    
    private void setMovieList(List<Movie> movies, VBox vBox) {
        for (Movie m : movies) {
            HBox tempHBox = new HBox();
            Label movieName = new Label(m.getTitle());
            Label streamingServices = new Label(getStreamingServicesText(m));
            tempHBox.getChildren().addAll(movieName, streamingServices);
            vBox.getChildren().add(tempHBox);
        }
    }
    
    private void fetchMoviesAsync(MovieDataController mdc, String type) {
        
        VBox vBox = ("popular".equals(type)) ? popularMoviesVBox : topRatedMoviesVBox;
        Label loadingLabel = ("popular".equals(type)) ? popularMoviesLoadingLabel : topRatedMoviesLoadingLabel;
        
        CompletableFuture.supplyAsync(() -> {
            if("popular".equals(type)) {
                return mdc.getPopularMovies();
            } else {
                return mdc.getTopRatedMovies();
            }
        })
            .thenAccept(moviesResponse -> {
                Platform.runLater(() -> vBox.getChildren().remove(loadingLabel));
                if (moviesResponse != null) {
                    List<Movie> tempMovieList = moviesResponse.getResults();
                    // Update UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        setMovieList(tempMovieList, vBox);
                    });
                } else {
                    System.out.println(loadingLabel.getText() + " failed.");
                }
            });
    }
    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
