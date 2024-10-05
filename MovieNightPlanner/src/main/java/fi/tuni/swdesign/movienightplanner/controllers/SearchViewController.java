package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.App;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SearchViewController {
    
    @FXML ListView<Label> popularMoviesLView;
    @FXML ListView<Label> topRatedMoviesLview;
    @FXML VBox mainView;
    @FXML ImageView profilePic;

    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies");
    
    @FXML
    public void initialize(){
        
        profilePic. addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            try {
                App.setRoot("ProfileView");
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        
        //TODO: Background settings
//        mainView.setBackground(
//            new Background(
//                    Collections.singletonList(new BackgroundFill(
//                            Color.WHITE, 
//                            new CornerRadii(500), 
//                            new Insets(10))),
//                    Collections.singletonList(new BackgroundImage(
//                            new Image(this.getClass().getResource("/images/movie_reel.jpeg").toString(), 1440, 1020, false, false),
//                            BackgroundRepeat.NO_REPEAT,
//                            BackgroundRepeat.NO_REPEAT,
//                            BackgroundPosition.CENTER,
//                            BackgroundSize.DEFAULT))));
        
        // Add "Loading" placeholders to the ListViews initially
        popularMoviesLView.setPlaceholder(popularMoviesLoadingLabel);
        topRatedMoviesLview.setPlaceholder(topRatedMoviesLoadingLabel);
        
        popularMoviesLView.setOrientation(Orientation.HORIZONTAL);
        topRatedMoviesLview.setOrientation(Orientation.HORIZONTAL);
                
        MovieDataController mdc = new MovieDataController();
        
        // TODO:
        // This function doesn't need be called each time.
        // We can fetch this data only on first time and save to a local json
        if(mdc.getStreamProviderMap() == null)
            mdc.fetchStreamingProviders();

        
        // Fetch Popular Movies Asynchronously
        fetchMoviesAsync(mdc, "popular");

        // Fetch Top-Rated Movies Asynchronously
        fetchMoviesAsync(mdc, "top");
       
    }
    
    // Helper function to get the streaming services as a text string
    // TEMPRORARY: not needed in the final product
    private String getStreamingServicesText(Movie movie) {
        StringBuilder sTxt = new StringBuilder("  - ");
        for (StreamingProvider stream : movie.getStreamingProviders()) {
            sTxt.append(stream.getProviderName()).append(" ");
        }
        return sTxt.toString();
    }
    
    // Add the movie label elements to ListView
    private void setMovieListView(List<Movie> movies, ListView lView) {
        ArrayList<Label> labelList = new ArrayList();
        for (Movie m : movies) {
            String movieName = m.getTitle();
            String streamingServices = getStreamingServicesText(m);
            Label labelToAdd = new Label(movieName + streamingServices);
            labelList.add(labelToAdd);
        }
        ObservableList<Label> labels = FXCollections.observableArrayList(labelList);
        lView.setItems(labels);
    }
    
    private void fetchMoviesAsync(MovieDataController mdc, String type) {
        
        ListView lView = ("popular".equals(type)) ? popularMoviesLView : topRatedMoviesLview;
        Label loadingLabel = ("popular".equals(type)) ? popularMoviesLoadingLabel : topRatedMoviesLoadingLabel;
        
        CompletableFuture.supplyAsync(() -> {
            if("popular".equals(type)) {
                return mdc.getPopularMovies();
            } else {
                return mdc.getTopRatedMovies();
            }
        })
            .thenAccept(moviesResponse -> {
                if (moviesResponse != null) {
                    List<Movie> tempMovieList = moviesResponse.getResults();
                    // Update UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        setMovieListView(tempMovieList, lView);
                    });
                } else {
                    System.out.println(loadingLabel.getText() + " failed.");
                }
            });
    }
}
