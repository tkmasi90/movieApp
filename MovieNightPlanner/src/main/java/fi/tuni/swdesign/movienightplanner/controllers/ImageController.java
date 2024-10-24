package fi.tuni.swdesign.movienightplanner.controllers;

import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Controller class for the image-related features.
 *
 * @author kian, Make
 */
public class ImageController {

    // The base URL for TMDb images
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    /**
     * This method takes the image path from TMDb API and sets the image in the specified ImageView.
     * If width is not provided, it loads the original size.
     *
     * @param imagePath The image path provided by the TMDb API (e.g., "/8nytsqL59SFJTVYVrN72k6qkGgJ.jpg")
     * @param fxId The fx:id of the ImageView where the image should be loaded.
     * @param width The desired width for the image (if null, it will use the "original" size).
     * @param scene The current scene where the ImageView is located.
     */
    public void loadImageIntoView(String imagePath, String fxId, Integer width, Scene scene) {

      // Run later so that the scene is ready first time around
      Platform.runLater(() -> {
        // Determine the size parameter for the URL: "w" + width or "original" if width is null
        String size = (width != null) ? "w" + width : "original";
        
        // Construct the full URL using the base URL, size, and image path
        String fullImageUrl = TMDB_IMAGE_BASE_URL + size + imagePath;

        if(imagePath == null) 
        {
            // use unknown image from images
            fullImageUrl = "/images/unknown.png";
        }
        
        ImageView imageView = (ImageView) scene.lookup("#" + fxId);

        // Load the image and set it in the ImageView
        if (imageView != null) {
            Image image = new Image(fullImageUrl);
            imageView.setImage(image);

            // Calculate the viewport to crop the image if necessary
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            double viewportWidth = imageView.getFitWidth();
            double viewportHeight = imageView.getFitHeight();

            double scaleX = viewportWidth / imageWidth;
            double scaleY = viewportHeight / imageHeight;
            double scale = Math.max(scaleX, scaleY);

            double newWidth = imageWidth * scale;
            double newHeight = imageHeight * scale;

            double x = (newWidth - viewportWidth) / 2 / scale;
            double y = (newHeight - viewportHeight) / 2 / scale;

            imageView.setViewport(new Rectangle2D(x, y, imageWidth - 2 * x, imageHeight - 2 * y));
        } else {
            System.out.println("ImageView with fx:id " + fxId + " not found.");
        }
      });
    }

    /**
     * Overloaded method to load image without specifying width, defaults to original size.
     *
     * @param imagePath The image path provided by the TMDb API.
     * @param fxId The fx:id of the ImageView where the image should be loaded.
     * @param scene The current scene where the ImageView is located.
     */
    public void loadImageIntoView(String imagePath, String fxId, Scene scene) {
        // Call the main method with null for the width
        loadImageIntoView(imagePath, fxId, null, scene);
    }
    
    public void loadLogosIntoMovieLabel(String imagePath, TilePane logoContainer) {
        
        ImageView logoImageView = new ImageView();
        // Run on a separate thread for image loading
        CompletableFuture.runAsync(() -> {
            // Determine the size parameter for the URL
            String size = "w45"; 
            String fullImageUrl = TMDB_IMAGE_BASE_URL + size + imagePath;

            // If imagePath is null, use the local fallback image
            if (imagePath == null) {
                fullImageUrl = this.getClass().getResource("/images/unknown.png").toString();
            }

            // Load the image asynchronously
            Image image = new Image(fullImageUrl, true);  // 'true' to load in the background

            // Once the image is loaded, update the UI on the JavaFX thread
            Platform.runLater(() -> {
                logoImageView.setImage(image);  // Set the image on the ImageView
                logoContainer.getChildren().add(logoImageView);  // Add to the container
            });
        });
    }
    
    public void loadPosterIntoMovieLabel(String imagePath, ImageView imageView ) {
        
        // Run on a separate thread for image loading
        CompletableFuture.runAsync(() -> {
            // Determine the size parameter for the URL
            String fullImageUrl = TMDB_IMAGE_BASE_URL + "w500" + imagePath;

            // If imagePath is null, use the local fallback image
            if (imagePath == null) {
                fullImageUrl = this.getClass().getResource("/images/unknown.png").toString();
            }

            // Load the image asynchronously
            Image image = new Image(fullImageUrl, true);  // 'true' to load in the background

            // Once the image is loaded, update the UI on the JavaFX thread
            Platform.runLater(() -> {
                imageView.setImage(image);  // Set the image on the ImageView
            });
        });
    }
}
