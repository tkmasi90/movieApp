package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.App;
import fi.tuni.swdesign.movienightplanner.models.Genre;
import fi.tuni.swdesign.movienightplanner.models.GenresResponse;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.utilities.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import org.controlsfx.control.CheckComboBox;
import org.apache.hc.client5.http.HttpResponseException;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

/**
 * Controller class for handling the search view of the application. It manages
 * movie lists, filter options, and transitions between views such as profile and movie details.
 * @author Make, ChatGPT(setComboBoxLogic, Javadoc comments)
 */
public class SearchViewController {
    
    @FXML TabPane movieViewSelect;
    @FXML VBox popularRatedView;
    @FXML Button filterButton;

    @FXML GridView<Label> filteredView;
    @FXML ListView<Label> popularMoviesLView;
    @FXML ListView<Label> topRatedMoviesLview;
    @FXML VBox mainView;
    @FXML GridPane streamers;
    @FXML CheckComboBox cbGenre;
    @FXML CheckComboBox cbAudio;    
    @FXML CheckComboBox cbSubtitle;
   
    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies");
    private final Label filteredMoviesLoadingLabel = new Label("Loading movies");

    private final MovieDataController mdc = new MovieDataController();
    private final Constants con = new Constants();
    private final ImageController ic = new ImageController();

    List<CheckBox> selectedProviders = new ArrayList<>();
    
    private SceneController sceneController;
    
    // HTTP Error Handling
    private int HTTPErrorCode;
    private String HTTPErrorMessage;
    
    /**
    * Sets the {@link SceneController} instance for this controller.
    * This method is used to inject the {@code SceneController} into the current context, allowing 
    * this controller to manage and switch scenes as needed.
    * 
    * @param sceneController the {@link SceneController} to be associated with this controller.
    */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
    /**
     * Initializes the search view components, sets backgrounds, populates movie lists,
     * and configures filter options.
     * This method is called automatically after the FXML components have been loaded.
     */
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
        
        // Set placeholders and initialize movie lists
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
        
        movieViewSelect.setFocusTraversable(false);
        
        // Set filter options and populate movie lists
        setFilterOptions();
        populateMovieListAsync(popularMoviesLoadingLabel, popularMoviesLView, con.getPopularMoviesUrl());
        populateMovieListAsync(topRatedMoviesLoadingLabel, topRatedMoviesLview, con.getTopRatedMoviesUrl());
        
        // Set streaming provider logos
        setStreamingProviders();

        
    }
    
    /**
     * Handles the action of switching to the profile view when the profile button is clicked.
     * 
     * @param event The action event triggered by clicking the profile button.
     * @throws IOException if an error occurs while switching the scene.
     */
    @FXML
    public void handleProfileButtonClick(ActionEvent event) throws IOException {
        if (sceneController == null) {
            System.out.println("SceneController is null in SearchViewController");
        } else {
            sceneController.switchToProfile(event);
        }
    }
    
    /**
     * Handles the action of applying filters when the filter button is clicked.
     * This filters the movies based on selected genres, audio, subtitles, and streaming providers.
     *
     * @param event The action event triggered by clicking the filter button.
     * @throws IOException if an error occurs during filtering.
     */
    @FXML
    private void handleFilterButtonClick(ActionEvent event) throws IOException {
        List<Integer> providers = getCheckedValues(selectedProviders);
        populateMovieListAsync(filteredMoviesLoadingLabel, filteredView, con.getFilteredUrl(providers));
        
        SingleSelectionModel<Tab> selectionModel = movieViewSelect.getSelectionModel();
        selectionModel.selectLast();
    }
    
    /**
     * Returns a list of streaming provider IDs that have been selected by the user.
     *
     * @param checkBoxes List of checkboxes representing streaming providers.
     * @return A list of integers representing the IDs of selected providers.
     */
    private List<Integer> getCheckedValues(List<CheckBox> checkBoxes) {
        List<Integer> checkedValues = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                checkedValues.add(Integer.valueOf(checkBox.getParent().getId()));
            }
        }
        return checkedValues;
    }
    
    /**
     * Sets filter options for genres, audio, and subtitles. It populates combo boxes
     * and applies logic for handling selections in the filtering options.
     */
    private void setFilterOptions() {
        // Populate combobox content for filtering
        populateGenreComboBox();
        
        // Populate Language and Subtitle comboboxes
        List<String> languages = con.getLanguages().stream()
            .map(Pair::getValue) // Get the second element (country name)
            .collect(Collectors.toList());
        cbAudio.getItems().addAll(languages);
        cbSubtitle.getItems().addAll(languages);
        cbAudio.getCheckModel().checkAll();
        cbSubtitle.getCheckModel().checkAll();
        
        // Set Combobox logic
        setComboBoxLogic(cbGenre);
        setComboBoxLogic(cbAudio);
        setComboBoxLogic(cbSubtitle);
        
        // Set Filter button onAction logic
        filterButton.setOnAction(event -> {
            try {
                handleFilterButtonClick(event);
            }
            catch (IOException ex) {
                System.err.println("Error with filter button");
            }
        });
        
    }
    
    /**
     * Applies logic to a CheckComboBox such that when one item is unchecked, 
     * all others are unchecked, and if none are checked, all items are selected.
     *
     * @param ccb The CheckComboBox to apply the logic to.
     */
    private void setComboBoxLogic(CheckComboBox ccb) {
        // Use an array to store the flag, making it mutable
        final boolean[] internalChange = {false};

        ccb.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            if (internalChange[0]) return; // Exit if an internal change is happening

            while (change.next()) {
                // Check if all items are initially checked and now an item is unchecked
                if (change.wasRemoved() && ccb.getCheckModel().getCheckedItems().size() == ccb.getItems().size() - 1) {
                    // Prevent further triggers during this process
                    internalChange[0] = true;

                    // Get the last unchecked item (the one that triggered the removal)
                    String lastUncheckedItem = change.getRemoved().get(0);

                    // Uncheck all items
                    ccb.getCheckModel().clearChecks();

                    // Check only the item that was previously unchecked
                    ccb.getCheckModel().check(lastUncheckedItem);

                    // Reset the flag
                    internalChange[0] = false;
                }

                // Handle the case when only one item is checked, and it gets unchecked
                else if (ccb.getCheckModel().getCheckedItems().isEmpty()) {
                    // Prevent further triggers during this process
                    internalChange[0] = true;

                    // Check all items back again
                    ccb.getCheckModel().checkAll();

                    // Reset the flag
                    internalChange[0] = false;
                }
            }
            
        });
    }


    /**
    * Populates a ListView with movie data. Each movie is displayed using custom labels 
    * that are loaded from an FXML file. These labels are cached for better performance.
    * 
    * @param movies the list of movies to display in the ListView
    * @param lView  the ListView component that will display the movie labels
    */
    private void setMovieListView(List<Movie> movies, ListView<Movie> lView) {
        ObservableList<Movie> movieList = FXCollections.observableArrayList(movies);
        lView.setItems(movieList);
        
        // Create a map to cache the graphics for each movie
        Map<Movie, StackPane> movieLabelCache = new HashMap<>();

        lView.setCellFactory(lv -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);

                if (empty || movie == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Check if the movie is already cached
                    if (movieLabelCache.containsKey(movie)) {
                        // Use the cached label (StackPane)
                        setGraphic(movieLabelCache.get(movie));
                    } else {
                        // Load the movie label and cache it
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("MovieLabel.fxml"));
                        try {
                            StackPane movieLabel = loader.load();  // Load the FXML
                            MovieLabelController mlController = loader.getController();  // Get the controller

                            // Populate the movie label with data from the movie object
                            mlController.addLogo(movie);
                            
                            mlController.addMovieImage(movie);

                            // Cache the graphic for later use
                            movieLabelCache.put(movie, movieLabel);

                            // Set the graphic for the current cell
                            setGraphic(movieLabel);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Handle clicks on the movie
                    setOnMouseClicked(event -> handleMovieClick(event, movie));
                }
            }
        });
    }
    
    /**
    * Populates a GridView with movie data. Each movie is displayed using custom labels 
    * that are loaded from an FXML file. These labels are cached for better performance.
    * 
    * @param movies the list of movies to display in the GridView
    * @param lView  the GridView component that will display the movie labels
    */
    private void setMovieGridView(List<Movie> movies, GridView<Movie> lView) {
        ObservableList<Movie> movieList = FXCollections.observableArrayList(movies);
        lView.setItems(movieList);
        
        // Create a map to cache the graphics for each movie
        Map<Movie, StackPane> movieLabelCache = new HashMap<>();

        lView.setCellFactory(lv -> new GridCell<Movie>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);

                if (empty || movie == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Check if the movie is already cached
                    if (movieLabelCache.containsKey(movie)) {
                        // Use the cached label (StackPane)
                        setGraphic(movieLabelCache.get(movie));
                    } else {
                        // Load the movie label and cache it
                        FXMLLoader loader = new FXMLLoader(App.class.getResource("MovieLabel.fxml"));
                        try {
                            StackPane movieLabel = loader.load();  // Load the FXML
                            MovieLabelController mlController = loader.getController();  // Get the controller

                            // Populate the movie label with data from the movie object
                            mlController.addLogo(movie);
                            
                            mlController.addMovieImage(movie);

                            // Cache the graphic for later use
                            movieLabelCache.put(movie, movieLabel);

                            // Set the graphic for the current cell
                            setGraphic(movieLabel);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Handle clicks on the movie
                    setOnMouseClicked(event -> handleMovieClick(event, movie));
                }
            }
        });
    }
    
    /**
     * Handles the event when a movie label is clicked. Switches the scene to the movie detail view.
     *
     * @param event The mouse event triggered by clicking a movie label.
     * @param movie The movie object whose details should be shown.
     */
    private void handleMovieClick(MouseEvent event, Movie movie) {
        // Logic to switch to the movie details view using SceneController
        try {
            if (sceneController != null) {
                sceneController.switchToMovieDetail(event, movie);  // Pass the movie to the SceneController
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * Sets the streaming providers by iterating over the streaming provider nodes
    * and assigning unique IDs from the provider list. After assigning the IDs, 
    * the method invokes {@link #setStreamingProviderLogos()} to apply the provider logos.
    */
    private void setStreamingProviders() {
        int index = 0;    
        for(Node spHbox : streamers.getChildren()) {
            spHbox.setId(Integer.toString(con.PROVIDER_IDS.get(index)));
            index++;
        }
        
        setStreamingProviderLogos();
    }
    
    /**
     * Sets the streaming provider logos in the search view by fetching and displaying them in the UI.
     * If providers are not loaded, it will display an error logo.
     */
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
                    
                    CheckBox checkBox = (CheckBox) imageView.getParent()
                                .lookup(".check-box");
                    

                    imageView.setOnMouseClicked(event -> {
                        if(checkBox.isSelected()) {
                            checkBox.setSelected(false);
                        }
                        else {
                            checkBox.setSelected(true);
                        }
                    });
                    
                    selectedProviders.add(checkBox);
                }
            }
        }
       
    }
   
        /**
     * Populates a ListView with a list of movies asynchronously by fetching movie data from the API.
     * 
     * @param loadingLabel The label to display while loading movies.
     * @param lView The ListView or GridView to populate with movie data.
     * @param url The URL to fetch movie data from.
     */
    private void populateMovieListAsync(Label loadingLabel, Node lView, String url) {       
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
                    
                    if (lView instanceof ListView) {
                        ListView<Movie> listView = (ListView<Movie>) lView;
                        setMovieListView(tempMovieList, listView);

                    } else if (lView instanceof GridView) {
                        GridView<Movie> gridView = (GridView<Movie>) lView;
                        setMovieGridView(tempMovieList, gridView);
                    }

                } else {
                    loadingLabel.setText("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
                }
            });
        });
    }
   
    /**
     * Populates the genre combo box asynchronously by fetching genre data from the API.
     */
    private void populateGenreComboBox() {
        // Fetch Genre-list from TMDB
        CompletableFuture.supplyAsync(() -> {
            GenresResponse temp = null;
            
            try {
                temp = mdc.fetchGenres(con.getGenresUrl());
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
    }
}
