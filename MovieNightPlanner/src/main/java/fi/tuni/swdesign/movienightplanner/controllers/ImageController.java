package fi.tuni.swdesign.movienightplanner.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ImageController {

    // The base URL for TMDb images
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    /**
     * This method takes the image path from TMDb API and sets the image in the specified ImageView.
     * If width is not provided, it loads the original size.
     * @param imagePath The image path provided by the TMDb API (e.g., "/8nytsqL59SFJTVYVrN72k6qkGgJ.jpg")
     * @param fxId The fx:id of the ImageView where the image should be loaded.
     * @param width The desired width for the image (if null, it will use the "original" size).
     * @param scene The current scene where the ImageView is located.
     */
    public void loadImageIntoView(String imagePath, String fxId, Integer width, Scene scene) {
        // Determine the size parameter for the URL: "w" + width or "original" if width is null
        String size = (width != null) ? "w" + width : "original";
        
        // Construct the full URL using the base URL, size, and image path
        String fullImageUrl = TMDB_IMAGE_BASE_URL + size + imagePath;
        
        // Fetch the ImageView using the fx:id
        ImageView imageView = (ImageView) scene.lookup("#" + fxId);

        // Load the image and set it in the ImageView
        if (imageView != null) {
            Image image = new Image(fullImageUrl);
            imageView.setImage(image);
        } else {
            System.out.println("ImageView with fx:id " + fxId + " not found.");
        }
    }

    /**
     * Overloaded method to load image without specifying width, defaults to original size.
     * @param imagePath The image path provided by the TMDb API.
     * @param fxId The fx:id of the ImageView where the image should be loaded.
     * @param scene The current scene where the ImageView is located.
     */
    public void loadImageIntoView(String imagePath, String fxId, Scene scene) {
        // Call the main method with null for the width
        loadImageIntoView(imagePath, fxId, null, scene);
    }
}
