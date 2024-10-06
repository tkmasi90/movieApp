package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.App;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.FetchedMovieLists;
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

    private final HTTPTools tools = new HTTPTools(); 
    private final MovieDataController mdc = new MovieDataController();
    private final FetchedMovieLists ml = FetchedMovieLists.getInstance();
    
    @FXML
    public void initialize(){
        
        profilePic. addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            try {
                //App.setRoot("/fi/tuni/swdesign/movienightplanner/ProfileView");
                App.setRoot("ProfileView");
            } catch (IOException ex) {
                System.out.println(ex.getCause());
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
        
        // TODO:
        // This function doesn't need be called each time.
        // We can fetch this data only on first time and save to a local json
        if(mdc.getStreamProviderMap() == null)
            mdc.fetchStreamingProviders();

        // Populate Popular Movies List View
        if(ml.popularMovieListEmpty()) {
            // Fetch Popular Movies Asynchronously and set Popular Movie List
            fetchMoviesAsync(popularMoviesLoadingLabel, popularMoviesLView, POPULAR_MOVIES_URL);
        } else {
            // Set Popular Movie List if already fetched
            setMovieListView(ml.getPopularMovieList(), popularMoviesLView);
        }

        // Populate Top Rated Movies List View
        if(ml.topRatedMovieListEmpty()) {
            // Fetch Top-Rated Movies Asynchronously and set Top Rated Movies List
            fetchMoviesAsync(topRatedMoviesLoadingLabel, topRatedMoviesLview, TOP_RATED_MOVIES_URL);
        } else {
            // Set Top-Rated Movie List if already fetched
            setMovieListView(ml.getTopRatedMovieList(), topRatedMoviesLview);
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
    
    private void fetchMoviesAsync(Label loadingLabel, ListView lView, String url) {

        CompletableFuture.supplyAsync(() -> {
            return mdc.searchMovies(url);
        })
            .thenAccept(moviesResponse -> {
                Platform.runLater(() -> {
                if (moviesResponse != null) {
                    List<Movie> tempMovieList = moviesResponse.getResults();
                    if (url.contains("popularity.desc")) {
                        ml.setPopularMovieList(tempMovieList);
                    } else if (url.contains("vote_average.desc")) {
                        ml.setTopRatedMovieList(tempMovieList);
                    }
                    setMovieListView(tempMovieList, lView);
                } else {
                    loadingLabel.setText("Failed to load movies.");
                }
            });
            })
            .exceptionally(ex -> {
            Platform.runLater(() -> loadingLabel.setText("An error occurred: " + ex.getMessage()));
            return null;
            });
    }
    
    private final String POPULAR_MOVIES_URL = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s", tools.getProviders());
    private final String TOP_RATED_MOVIES_URL = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", tools.getProviders());
}
