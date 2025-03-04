package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.AppState;
import fi.tuni.swdesign.movienightplanner.models.Cast;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;

import java.util.concurrent.CompletableFuture;
import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;

import javafx.stage.Stage;

/**
 * Controller for the movie details view.
 *
 * @author kian, Copilot
 */
public class MovieDetailsController {
    private SceneController sceneController;
    private final ImageController imageController = new ImageController();
    private AppState appState;
    private Movie movie;

    private final HTTPTools tools = new HTTPTools();

    private final MovieDataController mdc = new MovieDataController();

    @FXML private Label titleDetailsLabel;
    @FXML private Label overviewLabel;
    @FXML private Label tmdbRatingLabel;
    @FXML private Label specsLabel;
    @FXML private Label taglineLabel;
    @FXML private Label writerLabel;
    @FXML private Label directorLabel;
    @FXML private Label writerPlaceholderLabel;
    @FXML private Label directorPlaceholderLabel;
    @FXML private Label streamingAtLabel;
    @FXML private Label starsLabel;

    @FXML private ImageView posterImage;
    @FXML private ImageView backdropImage;
    @FXML private ImageView firstProviderImage;
    @FXML private ImageView secondProviderImage;
    @FXML private ImageView thirdProviderImage;
    @FXML private ImageView fourthProviderImage;

    @FXML private ImageView star1Image;
    @FXML private ImageView star2Image;
    @FXML private ImageView star3Image;
    @FXML private ImageView star4Image;
    @FXML private ImageView star5Image;
    @FXML private ImageView star6Image;
    @FXML private Rectangle star1Rectangle;
    @FXML private Rectangle star2Rectangle;
    @FXML private Rectangle star3Rectangle;
    @FXML private Rectangle star4Rectangle;
    @FXML private Rectangle star5Rectangle;
    @FXML private Rectangle star6Rectangle;
    @FXML private Label star1CharacterLabel;
    @FXML private Label star1NameLabel;
    @FXML private Label star2CharacterLabel;
    @FXML private Label star2NameLabel;
    @FXML private Label star3CharacterLabel;
    @FXML private Label star3NameLabel;
    @FXML private Label star4CharacterLabel;
    @FXML private Label star4NameLabel;
    @FXML private Label star5CharacterLabel;
    @FXML private Label star5NameLabel;
    @FXML private Label star6CharacterLabel;
    @FXML private Label star6NameLabel;

    /**
     * Sets the SceneController for this controller.
     *
     * @param sceneController the SceneController to set
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * Sets the AppState for this controller.
     *
     * @param appState the AppState to set
     */
    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    /**
     * Navigates to the search view.
     *
     * @param event the ActionEvent that triggered this method
     */
    @FXML
    private void navigateToSearchView(ActionEvent event) {
        try {
            sceneController.switchToSearch(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the search view.
     *
     * @param event the ActionEvent that triggered this method
     * @throws java.io.IOException
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
    * Navigates to the search view.
    *
    * @param event the ActionEvent that triggered this method
     * @throws java.io.IOException
    */
    @FXML
    public void handleProfileButtonClick(ActionEvent event) throws IOException{
        if (sceneController == null) {
            System.out.println("SceneController is null in SearchViewController");
        } else {
            sceneController.switchToProfile(event);
        }
    }
    
    /**
     * Handles the action of the rate button.
     */
    @FXML
    private void handleRateButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fi/tuni/swdesign/movienightplanner/RateView.fxml"));
            Parent root = loader.load();
            RateViewController rateViewController = loader.getController();
            Stage rateStage = new Stage();
            rateStage.initModality(Modality.APPLICATION_MODAL);
            rateStage.setTitle("Rate Movie");
            rateStage.setScene(new Scene(root));
            rateViewController.setStage(rateStage);
            rateViewController.setAppState(appState); // Pass AppState
            rateViewController.setMovie(movie); // Pass Movie
            rateStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the movie details to be displayed.
     *
     * @param movie the Movie object containing the details to be displayed
     */
    public void setMovie(Movie movie) {
      this.movie = movie;

      // Images
      imageController.loadImageIntoView(movie.getPosterPath(), "posterImage", 300, sceneController.getMovieDetailScene());
      imageController.loadImageIntoView(movie.getBackdropPath(), "backdropImage", 300, sceneController.getMovieDetailScene());
      setProviderImages(movie);

      CompletableFuture.supplyAsync(() -> {
          // Fetch movie details
          mdc.fetchMovieDetails(movie);
          mdc.fetchMovieCredits(movie);
          return movie;
        }).thenAcceptAsync(fetchedMovie -> {
          try {
              setCast(movie);

              // Set labels
              titleDetailsLabel.setText(fetchedMovie.getFormattedTitle());
              overviewLabel.setText(fetchedMovie.getOverview());
              tmdbRatingLabel.setText(String.valueOf(fetchedMovie.getVoteAverage()));

              // details
              taglineLabel.setText(fetchedMovie.getMovieDetails().getTagline());
              specsLabel.setText(fetchedMovie.getMovieDetails().getFormattedSpecs());

              if( fetchedMovie.getCredits().getWriter() == null ) {
                  writerPlaceholderLabel.setText("");
                  writerLabel.setText("");
              } else {
                  writerPlaceholderLabel.setText("Writer - ");
                  writerLabel.setText(fetchedMovie.getCredits().getWriter());
              }

              if ( fetchedMovie.getCredits().getDirector() == null ) {
                  directorPlaceholderLabel.setText("");
                  directorLabel.setText("");
              } else {
                  directorPlaceholderLabel.setText("Director - ");
              }

              directorLabel.setText(fetchedMovie.getCredits().getDirector());


          } catch (Exception e) {
              e.printStackTrace();
          }
      }, Platform::runLater).exceptionally(ex -> {
          ex.printStackTrace();
          return null;
      });
    }

    /**
     * Clears the movie details from the view.
     */
    public void clearMovieDetails() {
        // Clear labels
        titleDetailsLabel.setText("");
        overviewLabel.setText("");
        tmdbRatingLabel.setText("");
        taglineLabel.setText("");
        specsLabel.setText("");
        writerPlaceholderLabel.setText("");
        writerLabel.setText("");
        directorLabel.setText("");

        // Clear images
        posterImage.setImage(null);
        backdropImage.setImage(null);
        firstProviderImage.setImage(null);
        secondProviderImage.setImage(null);

        // Clear cast images and labels
        star1Image.setImage(null);
        star2Image.setImage(null);
        star3Image.setImage(null);
        star4Image.setImage(null);
        star5Image.setImage(null);
        star6Image.setImage(null);
        star1CharacterLabel.setText("");
        star1NameLabel.setText("");
        star2CharacterLabel.setText("");
        star2NameLabel.setText("");
        star3CharacterLabel.setText("");
        star3NameLabel.setText("");
        star4CharacterLabel.setText("");
        star4NameLabel.setText("");
        star5CharacterLabel.setText("");
        star5NameLabel.setText("");
        star6CharacterLabel.setText("");
        star6NameLabel.setText("");
        star1Rectangle.setVisible(false);
        star2Rectangle.setVisible(false);
        star3Rectangle.setVisible(false);
        star4Rectangle.setVisible(false);
        star5Rectangle.setVisible(false);
        star6Rectangle.setVisible(false);
    }

    /**
     * Sets the provider images for the movie.
     *
     * @param movie the Movie object containing the provider details
     */
    private void setProviderImages(Movie movie) {
        // clear the images first
        firstProviderImage.setImage(null);
        secondProviderImage.setImage(null);
        thirdProviderImage.setImage(null);
        fourthProviderImage.setImage(null);

        List<StreamingProvider> providers = movie.getStreamingProviders();

        // Check if no streamers available
        if( providers.size() == 0 ) {
            streamingAtLabel.setText("");
            return;
        } else {
            streamingAtLabel.setText("Streaming at");
        }

        int i = 0;
        for( StreamingProvider provider : providers ) {
            if( i == 0 ) {
                imageController.loadImageIntoView(provider.getLogoPath(), "firstProviderImage", sceneController.getMovieDetailScene());
            } else if( i == 1 ) {
                imageController.loadImageIntoView(provider.getLogoPath(), "secondProviderImage", sceneController.getMovieDetailScene());
            } else if ( i == 2 ) {
                imageController.loadImageIntoView(provider.getLogoPath(), "thirdProviderImage", sceneController.getMovieDetailScene());
            } else if ( i == 3 ) {
                imageController.loadImageIntoView(provider.getLogoPath(), "fourthProviderImage", sceneController.getMovieDetailScene());
            } else {
              break;
            }
            i++;
        }


    }

    /**
     * Sets the cast details for the movie.
     *
     * @param movie the Movie object containing the cast details
     */
    private void setCast(Movie movie) {
        List<Cast> cast = movie.getCredits().getCast();
        int castSize = cast.size();

        if( castSize == 0 ) {
            starsLabel.setText("");
            return;
        } else {
            starsLabel.setText("Stars");
        }

        // Show and set data for available cast members
        for (int i = 0; i < castSize && i < 6; i++) {
            Cast actor = cast.get(i);
            switch (i) {
                case 0:
                    imageController.loadImageIntoView(actor.getProfilePath(), "star1Image", 200, sceneController.getMovieDetailScene());
                    star1CharacterLabel.setText(actor.getCharacter());
                    star1NameLabel.setText(actor.getName());
                    star1Image.setVisible(true);
                    star1Rectangle.setVisible(true);
                    star1CharacterLabel.setVisible(true);
                    star1NameLabel.setVisible(true);
                    break;
                case 1:
                    imageController.loadImageIntoView(actor.getProfilePath(), "star2Image", 200, sceneController.getMovieDetailScene());
                    star2CharacterLabel.setText(actor.getCharacter());
                    star2NameLabel.setText(actor.getName());
                    star2Image.setVisible(true);
                    star2Rectangle.setVisible(true);
                    star2CharacterLabel.setVisible(true);
                    star2NameLabel.setVisible(true);
                    break;
                case 2:
                    imageController.loadImageIntoView(actor.getProfilePath(), "star3Image", 200, sceneController.getMovieDetailScene());
                    star3CharacterLabel.setText(actor.getCharacter());
                    star3NameLabel.setText(actor.getName());
                    star3Image.setVisible(true);
                    star3Rectangle.setVisible(true);
                    star3CharacterLabel.setVisible(true);
                    star3NameLabel.setVisible(true);
                    break;
                case 3:
                    imageController.loadImageIntoView(actor.getProfilePath(), "star4Image", 200, sceneController.getMovieDetailScene());
                    star4CharacterLabel.setText(actor.getCharacter());
                    star4NameLabel.setText(actor.getName());
                    star4Image.setVisible(true);
                    star4Rectangle.setVisible(true);
                    star4CharacterLabel.setVisible(true);
                    star4NameLabel.setVisible(true);
                    break;
                case 4:
                    imageController.loadImageIntoView(actor.getProfilePath(), "star5Image", 200, sceneController.getMovieDetailScene());
                    star5CharacterLabel.setText(actor.getCharacter());
                    star5NameLabel.setText(actor.getName());
                    star5Image.setVisible(true);
                    star5Rectangle.setVisible(true);
                    star5CharacterLabel.setVisible(true);
                    star5NameLabel.setVisible(true);
                    break;
                case 5:
                    imageController.loadImageIntoView(actor.getProfilePath(), "star6Image", 200, sceneController.getMovieDetailScene());
                    star6CharacterLabel.setText(actor.getCharacter());
                    star6NameLabel.setText(actor.getName());
                    star6Image.setVisible(true);
                    star6Rectangle.setVisible(true);
                    star6CharacterLabel.setVisible(true);
                    star6NameLabel.setVisible(true);
                    break;
            }
        }
    }
}
