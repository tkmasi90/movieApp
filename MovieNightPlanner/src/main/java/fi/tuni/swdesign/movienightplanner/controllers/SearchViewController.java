package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Genre;
import fi.tuni.swdesign.movienightplanner.models.GenresResponse;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.SpokenLanguage;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.utilities.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.controlsfx.control.CheckComboBox;
import org.apache.hc.client5.http.HttpResponseException;

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

    private final MovieDataController mdc = new MovieDataController();
    private final Constants con = new Constants();
    
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
        mainView.setBackground(
            new Background(
                    Collections.singletonList(new BackgroundFill(
                            Color.WHITE, 
                            new CornerRadii(500), 
                            new Insets(10))),
                    Collections.singletonList(new BackgroundImage(
                            new Image(this.getClass().getResource("/images/background.png").toString(), mainView.getMaxWidth(), mainView.getMaxHeight(), false, false),
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
        if(mdc.getStreamProviderMap() == null) {
            try {
                mdc.fetchStreamingProviders();
            }
            catch (HttpResponseException ex) {
                this.HTTPErrorCode = ex.getStatusCode();              
                this.HTTPErrorMessage = ex.getReasonPhrase();
                System.err.println("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
            }
        }

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
    
    private void setStreamingProviders() {
        int index = 0;    
        for(Node spHbox : streamers.getChildren()) {
            spHbox.setId(Integer.toString(con.PROVIDER_IDS.get(index)));
            index++;
        }
        
        setStreamingProviderLogos();
    }
    
    private void setStreamingProviderLogos() {
       
        if(mdc.getStreamProviderMap() == null) {
            System.err.println("Could not load providers");
        }
        
        else{
            Set<Node> imageViews = streamers.lookupAll(".image-view");
            String urlPrefix = "https://media.themoviedb.org/t/p/original";
            
            // Loop through each ImageView
            for (Node node : imageViews) {

                int parentId = Integer.parseInt(node.getParent().getId());

                if (node instanceof ImageView) {
                    ImageView imageView = (ImageView) node;  

                    String logoUrl = urlPrefix + mdc.getStreamProviderMap()
                            .get(parentId).getLogoPath();

                    if (logoUrl == null) {
                        Image errorLogo = new Image(this.getClass()
                                .getResource("/images/errorLogo.png")
                                .toString(), true);

                        imageView.setImage(errorLogo);
                        continue;
                    }      

                    // Create a rectangle with the same width and height as the ImageView
                    Rectangle clip = new Rectangle(imageView.getFitWidth(),
                            imageView.getFitHeight());

                    // Set the corner radius for rounded edges
                    clip.setArcWidth(20);
                    clip.setArcHeight(20);

                    // Apply the clip to the ImageView
                    imageView.setClip(clip);

                    Image logoImage = new Image(logoUrl, true);
                    imageView.setImage(logoImage);

                    imageView.setOnMouseClicked(event -> {
                        CheckBox checkBox = (CheckBox) imageView.getParent()
                                .lookup(".check-box");
                        
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
                    loadingLabel.setText("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
                }
            });
            });
    }
   
    private void populateComboBoxes() {
        // Fetch Genre-list from TMDB
        CompletableFuture.supplyAsync(() -> {
            GenresResponse temp = null;
            
            try {
                temp = mdc.fetchGenres(GENRES_URL);
            } catch (HttpResponseException ex) {

                this.HTTPErrorCode = ex.getStatusCode();              
                this.HTTPErrorMessage = ex.getReasonPhrase();
                
                return null;
            }
            return temp;
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
                    System.err.println("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
                }
            });
        });
        
        // Fetch Spoken Language-list from TMDB
        CompletableFuture.supplyAsync(() -> {
            List<SpokenLanguage> temp = null;
            
            try {
                temp = mdc.fetchSpokenLanguages(AUDIO_URL);
            } catch (HttpResponseException ex) {
                this.HTTPErrorCode = ex.getStatusCode();              
                this.HTTPErrorMessage = ex.getReasonPhrase();
                
                return null;
            }
            return temp;
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
                    System.err.println("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
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
