package fi.tuni.swdesign.movienightplanner.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageController {

    // The base URL for TMDb images
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    /**
     * This method takes the image path from TMDb API and sets the image in the specified ImageView.
     * If width is not provided, it loads the original size.
     * @param imagePath The image path provided by the TMDb API (e.g., "/8nytsqL59SFJTVYVrN72k6qkGgJ.jpg")
     * @param imageView The ImageView where the image should be loaded.
     * @param width The desired width for the image (if null, it will use the "original" size).
     */
    public void loadImageIntoView(String imagePath, ImageView imageView, Integer width) {
        // Determine the size parameter for the URL: "w" + width or "original" if width is null
        String size = (width != null) ? "w" + width : "original";
        
        // Construct the full URL using the base URL, size, and image path
        String fullImageUrl = TMDB_IMAGE_BASE_URL + size + imagePath;
        
        // Load the image and set it in the ImageView
        Image image = new Image(fullImageUrl);
        imageView.setImage(image);
    }

    /**
     * Overloaded method to load image without specifying width, defaults to original size.
     * @param imagePath The image path provided by the TMDb API.
     * @param imageView The ImageView where the image should be loaded.
     */
    public void loadImageIntoView(String imagePath, ImageView imageView) {
        // Call the main method with null for the width
        loadImageIntoView(imagePath, imageView, null);
    }
}
