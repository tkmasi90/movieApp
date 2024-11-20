package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Genre;
import fi.tuni.swdesign.movienightplanner.models.GenresResponse;
import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.utilities.TMDbUtility;
import fi.tuni.swdesign.movienightplanner.utilities.LanguageCodes;
import fi.tuni.swdesign.movienightplanner.utilities.MovieGenres;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.HttpResponseException;
import org.controlsfx.control.CheckComboBox;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for handling the profile view of the application. 
 * This class is responsible for fetching and displaying the streaming providers.
 * 
 * @author Sviat, GH Copilot
 */
public class ProfileViewController {
    private SceneController sceneController;
    private SearchViewController searchViewController;
    private AppState appState;
    private final TMDbUtility tmdbUtil = new TMDbUtility();
    private final MovieDataController mdc = new MovieDataController();
    private final ImageController ic = new ImageController();

    // HTTP Error Handling
    private int HTTPErrorCode;
    private String HTTPErrorMessage;

    @FXML private GridPane streamers;
    @FXML private CheckComboBox<String> cbGenre;
    @FXML private CheckComboBox<String> cbAudio;    

    @FXML private Spinner<Integer> minRatingSpinner;

    @FXML private PieChart genresPieChart;
    @FXML private PieChart centuryPieChart;

    @FXML private ListView<String> watchHistoryListView;

    @FXML private VBox chartContainer;


    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
    public void setSearchViewController(SearchViewController searchViewController) {
        this.searchViewController = searchViewController;
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize(){
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
        
        setComboBoxLogic(cbGenre);
        setComboBoxLogic(cbAudio);
        
        // Set Streaming Provider IDs and logos for filtering.
        setStreamingProviders();
        setFilterOptions();
        
        // Set spinner for minimum rating
        initializeSpinner();
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

//                // Handle the case when only one item is checked, and it gets unchecked
//                else if (ccb.getCheckModel().getCheckedItems().isEmpty()) {
//                    // Prevent further triggers during this process
//                    internalChange[0] = true;
//
//                    // Check all items back again
//                    ccb.getCheckModel().checkAll();
//
//                    // Reset the flag
//                    internalChange[0] = false;
//                }
            }
            
        });
    }
    
    public void setFiltersFromState() {
        for(Node cb : streamers.lookupAll(".check-box")) {
            if (cb instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) cb;
                checkBox.setSelected(false);
                for(var prov : appState.getPrefProviders()) {
                    if (prov.equals(checkBox.getParent().getId())) {
                        checkBox.setSelected(true);
                    }
                }
            }
        }
                    
        cbGenre.getCheckModel().clearChecks();
        
        cbGenre.getCheckModel().checkIndices(
            appState.getPrefGenres().stream().mapToInt(o -> (int) o).toArray()
        );
       
        cbAudio.getCheckModel().clearChecks();
        cbAudio.getCheckModel().checkIndices(
            appState.getPrefAudio().stream().mapToInt(o -> (int) o).toArray()
        );
        
    }
    
    public void updateData() {
        populateGenresPieChart();
        populateCenturyPieChart();
        populateWatchHistory();
    }
    

    /**
     * Initializes spinner for minimum rating.
     */
    private void initializeSpinner() {
      // TODO: Set the spinner value to the minimum rating from the appState
      minRatingSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
    }

    /**
     * Populates the genres pie chart in the profile view.
     */
    private void populateGenresPieChart() {
        // Clear existing data
        genresPieChart.getData().clear();

        List<String> genres = appState.getRatedMovieGenres();
        Map<String, Integer> genreCounts = new HashMap<>();

        // Count the occurrences of each genre
        for (String genre : genres) {
            genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
        }

        // Create PieChart data
        List<PieChart.Data> pieChartData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : genreCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Set the data to the PieChart
        genresPieChart.setData(FXCollections.observableArrayList(pieChartData));
    }

    /**
     * Populates the century pie chart in the profile view.
     */
    private void populateCenturyPieChart() {
        // Clear existing data
        centuryPieChart.getData().clear();

        List<String> centuries = appState.getMoviesByCentury();
        Map<String, Integer> centuryCounts = new HashMap<>();

        // Count the occurrences of each century category
        for (String century : centuries) {
            centuryCounts.put(century, centuryCounts.getOrDefault(century, 0) + 1);
        }

        // Create PieChart data
        List<PieChart.Data> pieChartData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : centuryCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Set the data to the PieChart
        centuryPieChart.setData(FXCollections.observableArrayList(pieChartData));
    }

    /**
     * Sets the streaming providers by iterating over the streaming provider nodes
     * and assigning unique IDs from the provider list. After assigning the IDs, 
     * the method invokes {@link #setStreamingProviderLogos()} to apply the provider logos.
     * IT is required to fetch the providers before calling this method.
     */
    private void setStreamingProviders() {
        int index = 0;    
        for(Node spHbox : streamers.getChildren()) {
            spHbox.setId(Integer.toString(tmdbUtil.PROVIDER_IDS.get(index)));
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
        } else {
            Set<Node> imageViews = streamers.lookupAll(".image-view");
            String urlPrefix = "https://media.themoviedb.org/t/p/original";
            
            // Loop through each ImageView
            for (Node node : imageViews) {

                int parentId = Integer.parseInt(node.getParent().getId());

                if (node instanceof ImageView) {
                    ImageView imageView = (ImageView) node;  

                    String logoUrl = urlPrefix + mdc.getStreamProviderMap()
                            .get(parentId).getLogoPath();

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
                    

                }
            }
        }
    }

    /**
     * Populates list view of watch history
     */
    private void populateWatchHistory() {
      watchHistoryListView.getItems().clear();
      List<String> watchHistory = appState.getWatchHistory();
      Collections.reverse(watchHistory);
      watchHistoryListView.getItems().addAll(watchHistory);
    }

    /**
     * Populates the genre combo box.
     */
    private void populateGenreComboBox() {
        cbGenre.getItems().addAll(
                MovieGenres.getAllGenresByName().keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList())
        );
        cbGenre.getCheckModel().checkAll();
    }

    /**
     * Sets filter options for genres, audio, and subtitles. It populates combo boxes
     * and applies logic for handling selections in the filtering options.
     */
    private void setFilterOptions() {
        // Populate combobox content for filtering
        populateGenreComboBox();
        
        // Populate Language and Subtitle comboboxes
        List<String> languages = LanguageCodes.getAllLanguageNames();
        cbAudio.getItems().addAll(languages);
    }

    /**
     * Navigates to the search view when the button is clicked.
     * 
     * @param event the ActionEvent object
     */
    @FXML
    private void navigateToSearchView(ActionEvent event) {
        try {
            sceneController.switchToSearch(event); // Delegate scene switching to SceneController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void savePreferences(ActionEvent event) {
        
        try {
            List<String> checkedProviders = new ArrayList<>();
            for(Node cb : streamers.lookupAll(".check-box")) {
                if (cb instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) cb;
                    if (checkBox.isSelected()) {
                        checkedProviders.add(checkBox.getParent().getId());
                    }
                }
            }       
            appState.setPrefProviders(checkedProviders);
            appState.setPrefAudio(cbAudio.getCheckModel().getCheckedIndices());
            appState.setPrefGenres(cbGenre.getCheckModel().getCheckedIndices());
            appState.setPrefMinRating(minRatingSpinner.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchViewController.updateFilters();
    }


    /**
     * sets the appstate
     */
    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    public VBox getChartContainer() {
        return chartContainer;
    }
}