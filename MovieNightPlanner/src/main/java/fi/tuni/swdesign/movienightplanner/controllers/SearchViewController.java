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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
import javafx.scene.control.Button;
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

    @FXML GridView<Movie> filteredView;
    @FXML ListView<Movie> popularMoviesLView;
    @FXML ListView<Movie> topRatedMoviesLview;
    @FXML VBox mainView;
    @FXML GridPane streamers;
    @FXML CheckComboBox cbGenre;
    @FXML CheckComboBox cbAudio;    
    @FXML ComboBox cbSubtitle;
    @FXML StackPane filterStackPane;
    @FXML Button filterButton;
   
    private final Label popularMoviesLoadingLabel = new Label("Loading popular movies...");
    private final Label topRatedMoviesLoadingLabel = new Label("Loading top-rated movies...");
    private final Label filteredMoviesLoadingLabel = new Label("Click 'Show Results' to search for movies");

    private final MovieDataController mdc = new MovieDataController();
    private final SubtitleDataController sdc = new SubtitleDataController();
    private final TMDbUtility tmdbUtil = new TMDbUtility();
    private final ImageController ic = new ImageController();

    private final List<CheckBox> selectedProviders = new ArrayList<>();
    private Integer minRating;
    
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
        setupLoadingLabels();
        
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
    
    // Method to set up GridView with a loading label
    private void setupLoadingLabels() {
        
        popularMoviesLoadingLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: black;");
        popularMoviesLView.setPlaceholder(popularMoviesLoadingLabel);
        topRatedMoviesLoadingLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: black;");
        topRatedMoviesLview.setPlaceholder(topRatedMoviesLoadingLabel);

        popularMoviesLView.setOrientation(Orientation.HORIZONTAL);
        topRatedMoviesLview.setOrientation(Orientation.HORIZONTAL);

        filteredMoviesLoadingLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: black;");

        filterStackPane.getChildren().addAll(filteredMoviesLoadingLabel);
        // Center the loading label in the container
        StackPane.setAlignment(filteredMoviesLoadingLabel, Pos.CENTER);
    }

    // Show the loading label
    private void showLoadingLabel() {
        filteredMoviesLoadingLabel.setVisible(true);
        filteredMoviesLoadingLabel.setManaged(true);
    }

    // Hide the loading label
    private void hideLoadingLabel() {
        filteredMoviesLoadingLabel.setVisible(false);
        filteredMoviesLoadingLabel.setManaged(false);
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
        // Disable the search button while the task is ongoing
        filterButton.setDisable(true);
        filteredView.setVisible(false);

        // Set loading label
        filteredMoviesLoadingLabel.setText("Loading movies, please wait...");

        filteredView.getItems().clear();

        // Reset filter page counter
        filterPage = 1;

        // Create the filtered URL
        String filteredUrl = createFilteredMoviesUrl();

        // Start a new task to fetch movies
        CompletableFuture.runAsync(() -> {
            populateMovieListAsync(
                filteredMoviesLoadingLabel,
                filteredView,
                filteredUrl,
                false
            );
        });
        // Switch to the last tab after the search
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
   

    private void populateMovieListAsync(Label loadingLabel, Node lView, String url, boolean append) {
        if (lView instanceof ListView) {
            // Existing ListView population logic for popular movies
            populateListViewAsync(loadingLabel, (ListView<Movie>) lView, url, append);
        } else if (lView instanceof GridView) {
            // Populate GridView for filtered movies (multiple pages)
            populateGridViewAsync((GridView<Movie>) lView);
        }
    }
    
    /**
     * Populates a ListView asynchronously by fetching movie data from the given URL.
     * If the `append` parameter is true, new movies are added to the existing list.
     * If `append` is false, the current list is replaced with the new set of movies.
     * 
     * @param loadingLabel The label to display while loading the movie list.
     * @param listView The ListView to populate with movie items.
     * @param url The URL to fetch movie data from.
     * @param append If true, new movies are appended to the existing list; otherwise, the list is replaced.
     */
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
    
    /**
     * Loads more movies into the specified ListView by determining the appropriate URL and fetching new results.
     * The new results are then appended to the existing list of movies.
     * 
     * @param lView The ListView to which new movie results will be appended.
     */
    private void loadMoreMovies(Node lView) {
        String url = determineUrlForNode(lView);
        if (url != null) {
            populateMovieListAsync(null, lView, url, true); // Append new results
        }
    }
    
    /**
     * Populates a GridView asynchronously by fetching movie data across multiple pages.
     * This method will start fetching from the first page and continue until 10 pages are retrieved.
     * 
     * @param lView The GridView to populate with movie items.
     */
    private void populateGridViewAsync(GridView<Movie> lView) {
        // Start the fetching process with the first page
        fetchNextPage(lView);
    }
    
    /**
     * Fetches the next page of movie data and adds it to the provided list of movies.
     * Once the movies are loaded, the GridView is updated, and the next page is fetched recursively.
     * The process stops after 10 pages.
     * 
     * @param lView The GridView to populate with movie items.
     * @param allMovies The list of all movies fetched so far, which will be updated with the new results.
     */
private void fetchNextPage(GridView<Movie> lView) {
    // Disable the search button before starting the fetch
    filterButton.setDisable(true);

    showLoadingLabel();

    ObservableList<Movie> allMovies = FXCollections.observableArrayList(lView.getItems());
    Set<Movie> existingMovies = new HashSet<>(allMovies);

    if (filterPage <= 10) { // Limit to 10 pages
        CompletableFuture.supplyAsync(() -> {
            MoviesResponse temp = null;
            try {
                temp = mdc.fetchMoviesResponse(determineUrlForNode(lView));
                if(filterPage > temp.getTotalPages()) {
                    filterButton.setDisable(false);
                    return null;
                }
            } catch (HttpResponseException ex) {
                this.HTTPErrorCode = ex.getStatusCode();
                this.HTTPErrorMessage = ex.getReasonPhrase();
                return null;
            }
            return temp;
        }).thenAccept(moviesResponse -> {
            Platform.runLater(() -> {
                if (moviesResponse != null) {
                    List<Movie> newMovies = moviesResponse.getResults();
                    
                    // Filter out already displayed movies
                    newMovies.removeIf(existingMovies::contains);

                    // If subtitle language is selected, filter by that too
                    if (cbSubtitle.getSelectionModel().getSelectedIndex() != 0) {
                        String subtitle = LanguageCodes.getCountryCodeFromName(
                                (String) cbSubtitle.getSelectionModel().getSelectedItem());

                        // Perform subtitle filtering asynchronously
                        CompletableFuture.supplyAsync(() -> 
                            newMovies.stream()
                                     .filter(movie -> sdc.areSubtitlesAvailable(movie.getId(), subtitle))
                                     .collect(Collectors.toList())
                        ).thenAccept(filteredMovies -> {
                            Platform.runLater(() -> {
                                allMovies.addAll(filteredMovies); // Add new page of movies

                                lView.setItems(allMovies);
                                setMovieGridView(allMovies, lView); // Update the GridView after each page

                                filterPage = 100;
                                filteredView.setVisible(true);
                                if(!allMovies.isEmpty())
                                    hideLoadingLabel();
                            });
                        });
                    } else {
                        allMovies.addAll(newMovies);
                        lView.setItems(allMovies);
                        setMovieGridView(allMovies, lView); // Update the GridView after each page

                        filterPage++;
                        filteredView.setVisible(true);
                        fetchNextPage(lView); // Continue fetching the next page

                        if(!allMovies.isEmpty())
                            hideLoadingLabel();
                    }
                } else {
                    // Handle errors and hide loading label
                    filteredMoviesLoadingLabel.setText("Failed to load movies. Please try again.");
                }
            });
        }).exceptionally(ex -> {
            // Handle any exception, ensure the button is re-enabled in case of an error
            Platform.runLater(() -> {
                filteredMoviesLoadingLabel.setText("Failed to load movies. Please try again.");
            });
            ex.printStackTrace();
            return null;
        }).thenRun(() -> {
            // This block runs once the entire task completes, either success or error
            // Check if we've reached the 10-page limit or not
            Platform.runLater(() -> {
                if (filterPage > 10 || allMovies.isEmpty()) {
                    // Re-enable the search button once all pages are fetched or on error
                    filterButton.setDisable(false);
                }
            });
        });
    } else {
        // If no more pages to fetch, re-enable the search button
        if (allMovies.isEmpty()) {
            filteredMoviesLoadingLabel.setText("No movies found");
        }
        // Ensure the button is enabled if we're done fetching
        Platform.runLater(() -> {
            filterButton.setDisable(false);
        });
    }
}



    /**
     * Determines the appropriate URL for the movie list based on the given ListView node.
     * This method selects the correct URL for popular movies, top-rated movies, or filtered movies.
     * 
     * @param lView The ListView to determine the URL for.
     * @return The URL to fetch movie data from, or null if no URL could be determined.
     */
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
    
    /**
     * Creates a URL for fetching filtered movie data based on the selected genres, audio, providers, and minimum rating.
     * 
     * @return The URL to fetch filtered movie data from.
     */
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

        return tmdbUtil.getFilteredUrl(filterPage, genres, audio, providers, minRating.toString());
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
                    .forEach(cb -> {
                        cb.setSelected(true);
                        selectedProviders.add(cb);
                    });  
        }
        else {
            // Update Provider Selection
            for(Node spHbox : streamers.getChildren()) {
                CheckBox checkBox = (CheckBox) spHbox.lookup(".check-box");
                checkBox.setSelected(false);
                for(String id : prefProviders)
                    if(id.equals(spHbox.getId())) {
                        checkBox.setSelected(true);
                        selectedProviders.add(checkBox);
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
        
        
        minRating = appState.getPrefMinRating()== null ? 0 : appState.getPrefMinRating();
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
