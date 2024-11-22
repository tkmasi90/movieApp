package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.AppState;

import java.io.IOException;

import org.apache.hc.client5.http.HttpResponseException;
import org.controlsfx.control.CheckComboBox;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Controller class for managing the profile view in the application. 
 * It handles displaying user preferences, watch history, and statistical data such as pie charts.
 * The class also facilitates saving and updating user preferences.
 * 
 * @author Sviat, GH Copilot, Markus, Kian
 */
public class ProfileViewController {
    private SceneController sceneController;
    private SearchViewController searchViewController;
    private FilterViewController filterViewController;
    
    private AppState appState;
    private final MovieDataController mdc = new MovieDataController();

    // HTTP Error Handling
    private int HTTPErrorCode;
    private String HTTPErrorMessage;

    @FXML private GridPane streamers;
    @FXML private CheckComboBox<String> cbGenre;
    @FXML private CheckComboBox<String> cbAudio;    
    @FXML private Spinner<Integer> minRatingSpinner;
    @FXML private Spinner<Integer> piechartSpinner;
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
        
    public void setFilterViewController(FilterViewController filterViewController) {
        this.filterViewController = filterViewController;
    }
    
    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    /**
     * Initializes the profile view controller. 
     * Fetches streaming provider data if not already loaded and sets up the rating spinner.
     */
    @FXML
    public void initialize(){

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
        
        // Set spinner for minimum rating
        initializeSpinner();

        // Set spinner for pie chart
        initializePiechartSpinner();
    }
    
    /**
     * Initializes filter options for genres, audio, and providers.
     */
    public void initializeFilters() {
        // Set filter options and populate movie lists
        filterViewController.setFilterOptions(cbAudio, cbGenre, null);
        filterViewController.setProviders(streamers, mdc);
    }
    
    /**
     * Applies user preferences from the application state to the UI components.
     */
    public void setFilterDataFromState() {
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
        // Set genres and audio checkboxes
        cbGenre.getCheckModel().clearChecks();
        cbGenre.getCheckModel().checkIndices(
            appState.getPrefGenres().stream().mapToInt(o -> (int) o).toArray()
        );
        cbAudio.getCheckModel().clearChecks();
        cbAudio.getCheckModel().checkIndices(
            appState.getPrefAudio().stream().mapToInt(o -> (int) o).toArray()
        );
        // Set minimum rating
        Integer minRating = appState.getPrefMinRating();
        if(minRating == null) {
            minRatingSpinner.getValueFactory().setValue(1);
        } else {
            minRatingSpinner.getValueFactory().setValue(appState.getPrefMinRating());
        }
    }
    
    /**
     * Updates the data displayed in the profile view, such as pie charts and watch history.
     */
    public void updateData() {
        populateGenresPieChart();
        populateCenturyPieChart();
        populateWatchHistory();
    }

    /**
     * Initializes spinner for minimum rating.
     */
    private void initializeSpinner() {
      minRatingSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }

    /**
     * Initializes spinner for pie chart.
     */
    private void initializePiechartSpinner() {
      piechartSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }

    /**
     * Handles the action of the apply button.
     *
     * @param event the ActionEvent that triggered this method
     */
    @FXML
    private void handleApplyMinButtonAction(ActionEvent event) {
        int minRating = piechartSpinner.getValue();
        populateGenresPieChart(minRating);
        populateCenturyPieChart(minRating);
    }

    /**
     * Populates the genres pie chart in the profile view.
     */
    private void populateGenresPieChart() {
        populateGenresPieChart(1); // Default to minimum rating of 1
    }

    /**
     * Populates the genres pie chart in the profile view.
     *
     * @param minRating the minimum rating to filter by
     */
    private void populateGenresPieChart(int minRating) {
        CompletableFuture.supplyAsync(() -> {
            List<String> genres = appState.getRatedMovieGenresByRating(minRating);
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
            
            return pieChartData;
        }).thenAccept(pieChartData -> {
                Platform.runLater(() -> {
                // Clear existing data
                genresPieChart.getData().clear();
                // Set the data to the PieChart
                genresPieChart.setData(FXCollections.observableArrayList(pieChartData));
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    /**
     * Populates the century pie chart with data based on user-rated movie release centuries.
     */
    private void populateCenturyPieChart() {
        populateCenturyPieChart(1); // Default to minimum rating of 1
    }

    /**
     * Populates the century pie chart in the profile view.
     *
     * @param minRating the minimum rating to filter by
     */
    private void populateCenturyPieChart(int minRating) {
        
        CompletableFuture.supplyAsync(() -> {

            List<String> centuries = appState.getMoviesByCentury(minRating);
            Map<String, Integer> centuryCounts = new HashMap<>();
            
            // Count occurrences of each century
            if (centuries != null) {
                for (String century : centuries) {
                    centuryCounts.put(century, centuryCounts.getOrDefault(century, 0) + 1);
                }
            }

            // Create PieChart data
            List<PieChart.Data> pieChartData = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : centuryCounts.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            
            return pieChartData;

        }).thenAccept(pieChartData -> {
            Platform.runLater(() -> {
                // Set the data to the PieChart
                centuryPieChart.getData().clear();
                centuryPieChart.setData(FXCollections.observableArrayList(pieChartData));
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    /**
     * Populates the watch history list view with user's watch history.
     */
    private void populateWatchHistory() {
      watchHistoryListView.getItems().clear();
      List<String> watchHistory = appState.getWatchHistory();
      Collections.reverse(watchHistory);
      watchHistoryListView.getItems().addAll(watchHistory);
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

        /**
     * Navigates to the search view.
     *
     * @param event the ActionEvent that triggered this method
     */
    @FXML
    public void handleHomeButtonClick(ActionEvent event) throws IOException{
        if (sceneController == null) {
            System.out.println("SceneController is null in SearchViewController");
        } else {
            sceneController.switchToSearch(event);
        }
    }

    /**
     * Saves user preferences and updates the search filters.
     * 
     * @param event the ActionEvent triggered by the save button
     */
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
        searchViewController.updateFilterData();
    }

    /**
     * Returns the container for displaying charts in the profile view.
     * 
     * @return the VBox container for charts
     */
    public VBox getChartContainer() {
        return chartContainer;
    }
}