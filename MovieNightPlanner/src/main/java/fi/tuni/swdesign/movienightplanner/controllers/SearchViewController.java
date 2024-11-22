package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.App;
import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.utilities.TMDbUtility;
import fi.tuni.swdesign.movienightplanner.utilities.LanguageCodes;
import fi.tuni.swdesign.movienightplanner.utilities.MovieGenres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
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

    @FXML GridView<Label> filteredView;
    @FXML ListView<Label> popularMoviesLView;
    @FXML ListView<Label> topRatedMoviesLview;
    @FXML VBox mainView;
    @FXML GridPane streamers;
    @FXML CheckComboBox cbGenre;
    @FXML CheckComboBox cbAudio;    
    @FXML ComboBox cbSubtitle;
   
    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies");
    private final Label filteredMoviesLoadingLabel = new Label("Loading movies");

    private final MovieDataController mdc = new MovieDataController();
    private final SubtitleDataController sdc = new SubtitleDataController();
    private final TMDbUtility tmdbUtil = new TMDbUtility();
    private final ImageController ic = new ImageController();

    private final List<CheckBox> selectedProviders = new ArrayList<>();
    private Integer minRating  = 1;
    
    private Integer popPage;
    private Integer topPage;
    private Integer filterPage;
    
    private SceneController sceneController;
    FilterViewController filterViewController;
    private AppState appState;
    
    // HTTP Error Handling
    private int HTTPErrorCode;
    private String HTTPErrorMessage;
    
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
    public void setAppState(AppState appState) {
        this.appState = appState;
    }
    
    public void setFilterViewController(FilterViewController filterViewController) {
        this.filterViewController = filterViewController;
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
        
        popPage = 1;
        topPage = 1;
        filterPage = 1;

        movieViewSelect.setFocusTraversable(false);
        populateMovieListAsync(popularMoviesLoadingLabel, popularMoviesLView, tmdbUtil.getPopularMoviesUrl(popPage++), false);
        populateMovieListAsync(topRatedMoviesLoadingLabel, topRatedMoviesLview, tmdbUtil.getTopRatedMoviesUrl(topPage++), false);
                
    }
    
    public void initializeFilters() {
        // Set filter options and populate movie lists
        filterViewController.setFilterOptions(cbAudio, cbGenre, cbSubtitle);
        cbSubtitle.getItems().add(0, "All");
        cbSubtitle.getSelectionModel().selectFirst();
        filterViewController.setProviders(streamers, mdc);
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
        List<Integer> providers = filterViewController
                .getCheckedValues(selectedProviders);
        List<Integer> genres = new ArrayList();
        List<String> audio = new ArrayList();
        
        List<String> genresChecked = (List<String>) cbGenre
                .getCheckModel()
                .getCheckedItems();
        List<String> audioChecked = (List<String>) cbAudio
                .getCheckModel()
                .getCheckedItems();
        Integer lngLength = LanguageCodes.getLanguageListLength();
        
        genres.addAll(genresChecked
                .stream()
                .map(genreName -> MovieGenres.getGenreIdByName((String) genreName))
                .collect(Collectors.toList())
        );
        
        if(audioChecked.size() == lngLength) {
            audio.addAll(LanguageCodes.getAllCountryCodes());
        }
        else {
            for(String a : audioChecked) {
                audio.add(LanguageCodes.getCountryCodeFromName(a));
            }
        }
                
        populateMovieListAsync(
                filteredMoviesLoadingLabel,
                filteredView,
                tmdbUtil.getFilteredUrl(
                        filterPage++,
                        genres,
                        audio,
                        providers,
                        minRating.toString()
                ),
                false
        );
        
        SingleSelectionModel<Tab> selectionModel = movieViewSelect.getSelectionModel();
        selectionModel.selectLast();
    }

    /**
    * Populates a ListView with movie data. Each movie is displayed using custom labels 
    * that are loaded from an FXML file. These labels are cached for better performance.
    * 
    * @param movies the list of movies to display in the ListView
    * @param lView  the ListView component that will display the movie labels
    */
    private void setMovieListView(List<Movie> movies, ListView<Movie> lView, Runnable loadMoreAction) {
        ObservableList<Movie> movieList = FXCollections.observableArrayList(movies);
        lView.setItems(movieList);
        // Create a map to cache the graphics for each movie
        Map<Movie, StackPane> movieLabelCache = new HashMap<>();

        lView.setCellFactory(lv -> {
            return new ListCell<Movie>() {
                @Override
                protected void updateItem(Movie movie, boolean empty) {
                    super.updateItem(movie, empty);
                    FXMLLoader loader = new FXMLLoader(App.class.getResource("MovieLabel.fxml"));

                    try {
                        StackPane movieLabel = loader.load();  // Load the FXML
                        MovieLabelController mlController = loader.getController();  // Get the controller
                    
                        if (empty) {
                        setText(null);
                        setGraphic(null);
                        setStyle("");
                        setOnMouseClicked(null);
                        } else if (movie != null) {
                            // Check if the movie is already cached
                            if (movieLabelCache.containsKey(movie)) {
                                // Use the cached label (StackPane)
                                setGraphic(movieLabelCache.get(movie));
                            } else {

                                // Populate the movie label with data from the movie object
                                mlController.addLogo(movie);

                                mlController.setClipRectangleSize(458, 257);

                                mlController.addMovieImage(movie, 257);

                                // Cache the graphic for later use
                                movieLabelCache.put(movie, movieLabel);
                                // Set the graphic for the current cell
                                setGraphic(movieLabel);
                            }
                            // Handle clicks on the movie
                            setOnMouseClicked(event -> handleMovieClick(event, movie));
                        }
                        // null is used for "Load More Results"-label
                        else {
                            mlController.setClipRectangleSize(458, 257);
                            mlController.addShowMore(458, 257);
                            setGraphic(movieLabel);
                            movieLabel.setOnMouseClicked(event -> {
                                loadMoreAction.run();
                                });
                            setOnMouseClicked(null);
                        }
                        
                        // Make the cell background transparent
                            setStyle("-fx-background-color: transparent; -fx-padding: 10px;");
                            setPrefWidth(448);
                            setPrefHeight(247);
                    }
                    catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            };
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
        lView.setCellWidth(490);
        lView.setCellHeight(320);
//        lView.setVerticalCellSpacing(-8);
//        lView.setHorizontalCellSpacing(-6);
        
        // Create a map to cache the graphics for each movie
        Map<Movie, StackPane> movieLabelCache = new HashMap<>();

        lView.setCellFactory(lv -> new GridCell<Movie>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
                super.updateItem(movie, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                    setOnMouseClicked(null);
                } else if (movie != null) {
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
                            
                            mlController.setClipRectangleSize(490, 320);
                            
                            mlController.addMovieImage(movie, 320);

                            // Cache the graphic for later use
                            movieLabelCache.put(movie, movieLabel);

                            // Set the graphic for the current cell
                            setGraphic(movieLabel);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }                           
                    // Make the cell background transparent
                    setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

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
     * Populates a ListView with a list of movies asynchronously by fetching movie data from the API.
     * 
     * @param loadingLabel The label to display while loading movies.
     * @param lView The ListView or GridView to populate with movie data.
     * @param url The URL to fetch movie data from.
     */
    private void populateMovieListAsync(Label loadingLabel, Node lView, String url, boolean append) {
        if (lView instanceof ListView) {
            // Existing ListView population logic for popular movies
            populateListViewAsync(loadingLabel, (ListView<Movie>) lView, url, append);
        } else if (lView instanceof GridView) {
            // Populate GridView for filtered movies (multiple pages)
            populateGridViewAsync((GridView<Movie>) lView, url);
        }
    }
    
    private void populateListViewAsync(Label loadingLabel, ListView<Movie> listView, String url, boolean append) {
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
        }).thenAccept(moviesResponse -> {
            Platform.runLater(() -> {
                if (moviesResponse != null) {
                    List<Movie> movieList = moviesResponse.getResults();
                    if (append) {
                        listView.getItems().remove(null); // remove last "show more"-label
                        movieList.add(null); // add new "show more"-label to end of the list
                        listView.getItems().addAll(movieList);
                    } else {
                        movieList.add(null);
                        setMovieListView(movieList, listView, () -> loadMoreMovies(listView));
                    }
                } else {
                    loadingLabel.setText("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
                }
            });
        });
    }
    
    private void populateGridViewAsync(GridView<Movie> lView, String url) {
        List<Movie> allMovies = new ArrayList<>();
        AtomicInteger pageCounter = new AtomicInteger(1); // Keeps track of current page being fetched

        // Start the fetching process with the first page
        fetchNextPage(lView, allMovies, pageCounter);
    }
    
    private void fetchNextPage(GridView<Movie> lView, List<Movie> allMovies, AtomicInteger pageCounter) {
    int currentPage = pageCounter.getAndIncrement();  // Get and increment the page number
    if (currentPage <= 10) {  // Limit to 5 pages
        final int page = currentPage;
        CompletableFuture.supplyAsync(() -> {
            MoviesResponse temp = null;
            try {
                temp = mdc.fetchMoviesResponse(tmdbUtil.getFilteredUrl(page, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), minRating.toString()));
            } catch (HttpResponseException ex) {
                return null;  // If there's an error, return null
            }
            return temp;  // Return the fetched MoviesResponse
        }).thenAccept(moviesResponse -> {
            Platform.runLater(() -> {
                if (moviesResponse != null) {
                    allMovies.addAll(moviesResponse.getResults()); // Add new page of movies
                    setMovieGridView(allMovies, lView); // Update the GridView after each page
                    fetchNextPage(lView, allMovies, pageCounter);  // Continue fetching the next page
                }
            });
        });
    }
}

    
    private void loadMoreMovies(Node lView) {
        String url = determineUrlForNode(lView);
        if (url != null) {
            populateMovieListAsync(null, lView, url, true); // Append new results
        }
    }
    
    private String determineUrlForNode(Node lView) {
        if (lView == popularMoviesLView) {
            return tmdbUtil.getPopularMoviesUrl(popPage++);
        } else if (lView == topRatedMoviesLview) {
            return tmdbUtil.getTopRatedMoviesUrl(topPage++);
        } else if (lView == filteredView) {
            return createFilteredMoviesUrl();
        }
        return null;
    }

    private String createFilteredMoviesUrl() {
        List<Integer> providers = filterViewController
                .getCheckedValues(selectedProviders);
        List<Integer> genres = new ArrayList();
        List<String> audio = new ArrayList();
        
        List<String> genresChecked = (List<String>) cbGenre
                .getCheckModel()
                .getCheckedItems();
        List<String> audioChecked = (List<String>) cbAudio
                .getCheckModel()
                .getCheckedItems();
        Integer lngLength = LanguageCodes.getLanguageListLength();
        
        genres.addAll(genresChecked
                .stream()
                .map(genreName -> MovieGenres.getGenreIdByName((String) genreName))
                .collect(Collectors.toList())
        );
        
        if(audioChecked.size() == lngLength) {
            audio.addAll(LanguageCodes.getAllCountryCodes());
        }
        else {
            for(String a : audioChecked) {
                audio.add(LanguageCodes.getCountryCodeFromName(a));
            }
        }

        return tmdbUtil.getFilteredUrl(filterPage++, genres, audio, providers, minRating.toString());
    }


    /**
    * Updates the filter settings in the UI based on the user's preferences stored in the application state.
    * The method synchronizes the state of checkboxes and other UI components to reflect the user's
    * preferred streaming providers, genres, and audio options. It also sets the minimum rating filter.
    */
    public void updateFilterData() {
        var prefProviders = appState.getPrefProviders();
        if(prefProviders.isEmpty()) {
            streamers.lookupAll(".check-box").stream()
                    .filter(node -> node instanceof CheckBox)
                    .map(node -> (CheckBox) node)
                    .forEach(cb -> cb.setSelected(true));  
        }
        else {
            // Update Provider Selection
            for(Node spHbox : streamers.getChildren()) {
                CheckBox checkBox = (CheckBox) spHbox.lookup(".check-box");
                checkBox.setSelected(false);
                for(String id : prefProviders)
                    if(id.equals(spHbox.getId())) {
                        checkBox.setSelected(true);
                    }
            }
        }
        
        // Update Genre Selection
        int[] intsG = appState.getPrefGenres().stream()
                        .mapToInt(Integer::intValue)
                        .toArray();
        
        if(intsG.length == 0) {
            cbGenre.getCheckModel().checkAll();
        } else {
            cbGenre.getCheckModel().clearChecks();
            cbGenre.getCheckModel().checkIndices(intsG);
        }
        
        // Update Audio Selection
        int[] intsA = appState.getPrefAudio().stream()
                .mapToInt(Integer::intValue)
                .toArray();
        
        if(intsA.length == 0) {
            cbAudio.getCheckModel().checkAll();
        }
        else {
            cbAudio.getCheckModel().clearChecks();
            cbAudio.getCheckModel().checkIndices(intsA);
        }
        
        minRating = appState.getPrefMinRating();
    }
    
    /**
    * Sets the minimum rating for the filter.
    * This method updates the internal state to reflect the desired minimum rating.
    *
    * @param rating The minimum rating to be set (e.g., a value between 1 and 10).
    */
    public void setMinRating(Integer rating) {
        minRating = rating;
    }
}
