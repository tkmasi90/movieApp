package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.models.Genre;
import fi.tuni.swdesign.movienightplanner.models.GenresResponse;
import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.utilities.TMDbUtility;
import fi.tuni.swdesign.movienightplanner.utilities.LanguageCodes;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.HttpResponseException;
import org.controlsfx.control.CheckComboBox;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for handling the profile view of the application. 
 * This class is responsible for fetching and displaying the streaming providers.
 * 
 * @author Sviat, GH Copilot
 */
public class ProfileViewController {
    private SceneController sceneController;
    private AppState appState;
    private final TMDbUtility tmdbUtil = new TMDbUtility();
    private final MovieDataController mdc = new MovieDataController();
    private final ImageController ic = new ImageController();
    List<CheckBox> selectedProviders = new ArrayList<>();

    // HTTP Error Handling
    private int HTTPErrorCode;
    private String HTTPErrorMessage;

    @FXML GridPane streamers;
    @FXML CheckComboBox<String> cbGenre;
    @FXML CheckComboBox<String> cbAudio;    
    @FXML CheckComboBox<String> cbSubtitle;
    
    @FXML private PieChart genresPieChart;
    @FXML private PieChart centuryPieChart;

    @FXML private ListView<String> watchHistoryListView;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * Initializes the controller.
     */
    public void initializeView(){
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

        // Set Streaming Provider IDs and logos for filtering.
        setStreamingProviders();
        setFilterOptions();

        // Set genres pie chart
        populateGenresPieChart();

        // Set century pie chart
        populateCenturyPieChart();

        // Set watch history list view
        populateWatchHistory();
    }

    /**
     * Populates the genres pie chart in the profile view.
     */
    private void populateGenresPieChart() {
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
                    
                    selectedProviders.add(checkBox);
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
     * Populates the genre combo box asynchronously by fetching genre data from the API.
     */
    private void populateGenreComboBox() {
        // Fetch Genre-list from TMDB
        CompletableFuture.supplyAsync(() -> {
            GenresResponse temp = null;
            
            try {
                temp = mdc.fetchGenres(tmdbUtil.getGenresUrl());
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

                } else {
                    System.err.println("Error: " + this.HTTPErrorCode + " - " + this.HTTPErrorMessage);
                }
            });
        });
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
        cbSubtitle.getItems().addAll(languages);
    }

    /**
     * Navigates to the search view when the button is clicked.
     * 
     * @param event the ActionEvent object
     */
    @FXML
    private void navigateToSearchView(ActionEvent event) {
        System.out.println("Button clicked, navigating to Search View...");
        try {
            sceneController.switchToSearch(event); // Delegate scene switching to SceneController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * sets the appstate
     */
    public void setAppState(AppState appState) {
        this.appState = appState;
    }
}