package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.utilities.Constants;

import java.io.IOException;
import java.util.Set;

import org.apache.hc.client5.http.HttpResponseException;

import java.util.List;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
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
    private final Constants con = new Constants();
    private final MovieDataController mdc = new MovieDataController();
    private int HTTPErrorCode;
    private String HTTPErrorMessage;
    List<CheckBox> selectedProviders = new ArrayList<>();

    @FXML GridPane streamers;
    
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize(){
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

        // Set Streaming Provider IDs and logos for filtering.
        setStreamingProviders();
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
}