package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.hc.client5.http.HttpResponseException;

public class SearchViewController {
    
    @FXML ListView<Label> popularMoviesLView;
    @FXML ListView<Label> topRatedMoviesLview;
    @FXML VBox mainView;
    
    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies");

    private final HTTPTools tools = new HTTPTools(); 
    private final MovieDataController mdc = new MovieDataController();
    
    private SceneController sceneController;
    
    // HTTP Error Handling
    private int HTTPErrorCode;
    private String HTTPErrorMessage;
    
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
    @FXML
    public void initialize(){
                
        //TODO: Background settings
//        mainView.setBackground(
//            new Background(
//                    Collections.singletonList(new BackgroundFill(
//                            Color.WHITE, 
//                            new CornerRadii(500), 
//                            new Insets(10))),
//                    Collections.singletonList(new BackgroundImage(
//                            new Image(this.getClass().getResource("/images/movie_reel.jpeg").toString(), mainView.getPrefWidth(), mainView.getPrefHeight(), false, false),
//                            BackgroundRepeat.NO_REPEAT,
//                            BackgroundRepeat.NO_REPEAT,
//                            BackgroundPosition.CENTER,
//                            BackgroundSize.DEFAULT))));
        
        // Add "Loading" placeholders to the ListViews initially
        popularMoviesLView.setPlaceholder(popularMoviesLoadingLabel);
        topRatedMoviesLview.setPlaceholder(topRatedMoviesLoadingLabel);
        
        popularMoviesLView.setOrientation(Orientation.HORIZONTAL);
        topRatedMoviesLview.setOrientation(Orientation.HORIZONTAL);
        
        // TODO:
        // This function doesn't need be called each time.
        // We can fetch this data only on first time and save to a local json
        if(mdc.getStreamProviderMap() == null)
            mdc.fetchStreamingProviders();

        // Populate Popular Movies List View
        populateMovieListAsync(popularMoviesLoadingLabel, popularMoviesLView, POPULAR_MOVIES_URL);

        // Populate Top Rated Movies List View
        populateMovieListAsync(topRatedMoviesLoadingLabel, topRatedMoviesLview, TOP_RATED_MOVIES_URL);
    }
    
    @FXML
    public void handleProfileButtonClick(ActionEvent event) throws IOException {
        if (sceneController == null) {
            System.out.println("SceneController is null in SearchViewController");
        } else {
            sceneController.switchToProfile(event);
        }
    }
    
    // Helper function to get the streaming services as a text string
    // TEMPORARY: not needed in the final product
    private String getStreamingServicesText(Movie movie) {
        StringBuilder sTxt = new StringBuilder("  - ");
        for (StreamingProvider stream : movie.getStreamingProviders()) {
            sTxt.append(stream.getProviderName()).append(" ");
        }
        return sTxt.toString();
    }
    
    private void handleMovieClick(MouseEvent event, Movie movie) {

        // Logic to switch to the movie details view
        try {
            if (sceneController == null) {
              return;
            }
            sceneController.switchToMovieDetail(event, movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Add the movie label elements to ListView
    private void setMovieListView(List<Movie> movies, ListView<Movie> lView) {
        ObservableList<Movie> movieList = FXCollections.observableArrayList(movies);
        lView.setItems(movieList);
    
        lView.setCellFactory(lv -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);
                if (empty || movie == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String movieName = movie.getTitle();
                    String streamingServices = getStreamingServicesText(movie);
                    Label label = new Label(movieName + streamingServices);
                    label.setOnMouseClicked(event -> handleMovieClick(event, movie));
                    setGraphic(label);
                }
            }
        });
    }
    
    private void populateMovieListAsync(Label loadingLabel, ListView lView, String url) {

        // Fetch movies from TMDB
        CompletableFuture.supplyAsync(() -> {
            
            MoviesResponse temp = null;
            try {
                temp = mdc.fetchMoviesResponse(url);
            } catch (HttpResponseException ex) {

                this.HTTPErrorCode = ex.getStatusCode();              
                this.HTTPErrorMessage = ex.getReasonPhrase();
                
                return null;
            }
            return temp;
        })
            // When fetch complete, update ListView
            .thenAccept(moviesResponse -> {
                Platform.runLater(() -> {
                if (moviesResponse != null) {
                    List<Movie> tempMovieList = moviesResponse.getResults();
                    setMovieListView(tempMovieList, lView);
                } else {
                    //loadingLabel.setText("Failed to load movies.");
                    
                    loadingLabel.setText("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
                }
            });
            });
//            // Give error message in case of exception
//            .exceptionally(ex -> {
//            Platform.runLater(() -> loadingLabel.setText("An error occurred: " + ex.getMessage()));
//            return null;
//            });
    }

    private final String POPULAR_MOVIES_URL = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s", tools.getProviders());
    private final String TOP_RATED_MOVIES_URL = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", tools.getProviders());
}
