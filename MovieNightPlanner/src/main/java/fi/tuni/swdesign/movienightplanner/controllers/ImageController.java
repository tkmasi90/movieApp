package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.utilities.TMDbUtility;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for handling image-related features in the application.
 * This includes loading images into ImageViews, handling logos, and managing
 * poster images for movies. Integrates with the TMDb API for image resources.
 * 
 * Dependencies include TMDbUtility and MovieDataController.
 * 
 * @author kian, Make
 */
public class ImageController {

    TMDbUtility tmdbUtil = new TMDbUtility();
    // The base URL for TMDb images
    String baseUrl = tmdbUtil.getImagesBaseUrl();

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
        String fullImageUrl = baseUrl + size + imagePath;

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
    
    /**
     * This method takes the logo path from TMDb API and sets the logo in the specified logo container.
     * @param imagePath The image path provided by the TMDb API (e.g., "/8nytsqL59SFJTVYVrN72k6qkGgJ.jpg")
     * @param logoContainer The TilePane where the logo image will be added.
     */
    public void loadLogosIntoMovieLabel(String imagePath, TilePane logoContainer) {
        ImageView logoImageView = new ImageView();
        // Run on a separate thread for image loading
        CompletableFuture.runAsync(() -> {
            // Determine the size parameter for the URL
            String size = "w45"; 
            String fullImageUrl = baseUrl + size + imagePath;

            // If imagePath is null, use the local fallback image
            if (imagePath == null) {
                fullImageUrl = this.getClass().getResource("/images/unknown.png").toString();
            }

            // Load the image asynchronously
            Image image = new Image(fullImageUrl, true);

            // Once the image is loaded, update the UI on the JavaFX thread
            Platform.runLater(() -> {
                logoImageView.setImage(image);
                logoContainer.getChildren().add(logoImageView);
            });
        });
    }
    
    /**
     * Loads a poster image from the TMDb API into the specified ImageView.
     * The poster image is resized to a specified height while maintaining its aspect ratio.
     * 
     * @param imagePath The image path from the TMDb API (e.g., "/poster.jpg").
     * @param imageView The ImageView where the poster image will be displayed.
     * @param height The desired height for the poster image.
     */
    public void loadPosterIntoMovieLabel(String imagePath, ImageView imageView, Integer height ) {
        String fullImageUrl = baseUrl + "w500" + imagePath;
        
        // If imagePath is null, use the local fallback image
        if (imagePath == null) {
            fullImageUrl = this.getClass().getResource("/images/grayBackground.png").toString();
        }
            
        Image image = new Image(fullImageUrl, true);
        // Run on a separate thread for image loading
        CompletableFuture.runAsync(() -> {
            // Once the image is loaded, update the UI on the JavaFX thread
            Platform.runLater(() -> {
                imageView.setImage(image);
                imageView.setFitHeight(height);
            });
        });
    }
    
    /**
     * Loads a streaming provider logo into an ImageView and applies a rounded
     * clip to the image. The method also returns the associated CheckBox for the
     * provider.
     * 
     * @param imageView The ImageView where the provider logo will be displayed.
     * @param mdc The MovieDataController providing the streaming provider data.
     * @return The CheckBox associated with the streaming provider.
     */
    public CheckBox loadProviderLogos(ImageView imageView, MovieDataController mdc) {
        String urlPrefix = "https://media.themoviedb.org/t/p/original";
        int parentId = Integer.parseInt(imageView.getParent().getId());
        String logoUrl = urlPrefix + mdc.getStreamProviderMap()
                            .get(parentId).getLogoPath();

        if (logoUrl == null) {
            Image errorLogo = new Image(this.getClass()
                    .getResource("/images/errorLogo.png")
                    .toString(), true);

            imageView.setImage(errorLogo);
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
        
        return checkBox;
    }
}
