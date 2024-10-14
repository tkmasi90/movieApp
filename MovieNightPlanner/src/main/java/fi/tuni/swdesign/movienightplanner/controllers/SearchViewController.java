package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Genre;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.SpokenLanguage;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.utilities.Constants;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.CheckComboBox;

public class SearchViewController {
    
    @FXML ListView<Label> popularMoviesLView;
    @FXML ListView<Label> topRatedMoviesLview;
    @FXML VBox mainView;
    @FXML GridPane streamers;
    @FXML CheckComboBox cbGenre;
    @FXML CheckComboBox cbAudio;
    @FXML CheckComboBox cbSubtitle;
    
    
    
    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies");

    private final HTTPTools httpTools = new HTTPTools(); 
    private final MovieDataController mdc = new MovieDataController();
    private final Constants con = new Constants();
    
    private SceneController sceneController;
    
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
    @FXML
    public void initialize(){
                
        //TODO: Background settings
        mainView.setBackground(
            new Background(
                    Collections.singletonList(new BackgroundFill(
                            Color.WHITE, 
                            new CornerRadii(500), 
                            new Insets(10))),
                    Collections.singletonList(new BackgroundImage(
                            new Image(this.getClass().getResource("/images/background.png").toString(), mainView.getPrefWidth(), mainView.getPrefHeight(), false, false),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT))));
        
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
        
        // Set Streaming Provider IDs and logos for filtering.
        setStreamingProviders();
        
        // Populate combobox content for filtering
        populateComboBoxes();
        
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
    
    private void setStreamingProviders() {
        int index = 0;    
        for(Node spHbox : streamers.getChildren()) {
            spHbox.setId(Integer.toString(con.PROVIDER_IDS.get(index)));
            index++;
        }
        
        setStreamingProviderLogos();
    }
    
    private void setStreamingProviderLogos() {
        Set<Node> imageViews = streamers.lookupAll(".image-view");
        String urlPrefix = "https://media.themoviedb.org/t/p/original";

        // Loop through each ImageView
        for (Node node : imageViews) {
            int parentId = Integer.parseInt(node.getParent().getId());
            
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                String logoUrl = urlPrefix + mdc.getStreamProviderMap().get(parentId).getLogoPath();
                
                // Create a rectangle with the same width and height as the ImageView
                Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());

                // Set the corner radius for rounded edges
                clip.setArcWidth(20);
                clip.setArcHeight(20);

                // Apply the clip to the ImageView
                imageView.setClip(clip);
                
                Image logoImage = new Image(logoUrl, true);
                imageView.setImage(logoImage);
                
                imageView.setOnMouseClicked(event -> {
                    CheckBox checkBox = (CheckBox) imageView.getParent().lookup(".check-box");
                    if(checkBox.isSelected()) {
                        checkBox.setSelected(false);
                    }
                    else {
                        checkBox.setSelected(true);
                    }
                });
            }
        }
    }
    
    private void populateMovieListAsync(Label loadingLabel, ListView lView, String url) {

        // Fetch movies from TMDB
        CompletableFuture.supplyAsync(() -> {
            return mdc.fetchMoviesResponse(url);
        })
            // When fetch complete, update ListView
            .thenAccept(moviesResponse -> {
                Platform.runLater(() -> {
                if (moviesResponse != null) {
                    List<Movie> tempMovieList = moviesResponse.getResults();
                    setMovieListView(tempMovieList, lView);
                } else {
                    loadingLabel.setText("Failed to load movies.");
                }
            });
            })
            // Give error message in case of exception
            .exceptionally(ex -> {
            Platform.runLater(() -> loadingLabel.setText("An error occurred: " + ex.getMessage()));
            return null;
            });
    }
   
    private void populateComboBoxes() {
        // Populate GenreList
        CompletableFuture.supplyAsync(() -> {
            return mdc.fetchGenres(GENRES_URL);
        })
                .thenAccept(genreResponse -> {
                    Platform.runLater(() -> {
                        if(genreResponse != null) {
                            List<String> genreList = genreResponse.getGenres()
                                    .stream()
                                    .map(Genre::getName)
                                    .collect(Collectors.toList()); 

                            cbGenre.getItems().addAll(genreList);
                            cbGenre.getCheckModel().checkAll();
                            
                        } else {
                            System.err.println("Failed to load genres");
                        }
                    });
                });
        
        // Populate LanguageList
        CompletableFuture.supplyAsync(() -> {
            return mdc.fetchSpokenLanguages(AUDIO_URL);
        })
        .thenAccept(audioList  -> {
            Platform.runLater(() -> {
                if (audioList != null) {
                    List<String> audioNames = audioList.stream()
                        .map(SpokenLanguage::getEnglishName)
                        .sorted()
                        .collect(Collectors.toList());

                    cbAudio.getItems().addAll(audioNames);
                    cbAudio.getCheckModel().checkAll();
                } else {
                    System.err.println("Failed to load languages");
                }
            });
        });
       
        
//        cbSubtitle = getComboBoxContent("subtitle");

    }
    
    
    private final String POPULAR_MOVIES_URL = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s", con.getProvidersString());
    private final String TOP_RATED_MOVIES_URL = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", con.getProvidersString());
    private final String GENRES_URL = String.format("https://api.themoviedb.org/3/genre/movie/list");
    private final String AUDIO_URL = String.format("https://api.themoviedb.org/3/configuration/languages");
    // TODO: private final String SUBTITLE_URL
}
