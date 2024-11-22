/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import fi.tuni.swdesign.movienightplanner.utilities.LanguageCodes;
import fi.tuni.swdesign.movienightplanner.utilities.MovieGenres;
import fi.tuni.swdesign.movienightplanner.utilities.TMDbUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;

/**
 * Controller class for managing filtering functionality in the application.
 * This class handles setting filter options for genres, languages, subtitles,
 * and streaming providers, as well as managing the logic for user interactions
 * with filter-related UI components.
 * 
 * Dependencies include TMDbUtility, ImageController, and MovieDataController.
 * 
 * @author Make, ChatGPT (javadoc comments)
 */
public class FilterViewController {
    TMDbUtility tmdbUtil = new TMDbUtility();
    ImageController ic = new ImageController();
    
    /**
     * Configures filter options for genres, audio, and subtitles.
     * Populates the provided CheckComboBoxes with genre and language options
     * and sets up user interaction logic for these filters.
     * 
     * @param cbAudio    The CheckComboBox for selecting audio languages.
     * @param cbGenre    The CheckComboBox for selecting movie genres.
     * @param cbSubtitle The ComboBox for selecting subtitle languages (optional, can be null).
     */
    public void setFilterOptions(CheckComboBox cbAudio, CheckComboBox cbGenre, ComboBox cbSubtitle) {
        // Populate combobox content for filtering
        populateGenreComboBox(cbGenre);
        
        // Populate Language and Subtitle comboboxes
        List<String> languages = LanguageCodes.getAllCountryCodes()
                .stream()
                .filter(sub -> !sub.equals("ko") && !sub.equals("ja"))
                .map(cc -> LanguageCodes.getNameFromCc(cc))
                .toList();
        cbAudio.getItems().addAll(languages);
        if(cbSubtitle != null)
            cbSubtitle.getItems().addAll(languages);
        
        // Set Combobox logic
        setComboBoxLogic(cbGenre);
        setComboBoxLogic(cbAudio);
    }
    
    /**
     * Populates the genre combo box with a sorted list of all available genres.
     * Marks all genres as selected by default.
     * 
     * @param cbGenre The CheckComboBox for selecting genres.
     */
    private void populateGenreComboBox(CheckComboBox cbGenre) {
        cbGenre.getItems().addAll(
                MovieGenres.getAllGenresByName().keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList())
        );
        cbGenre.getCheckModel().checkAll();
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
//
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
    
    /**
     * Retrieves the IDs of streaming providers that have been selected by the user.
     * 
     * @param checkBoxes A list of CheckBox nodes representing streaming providers.
     * @return A list of integers representing the IDs of selected providers.
     */
    public List<Integer> getCheckedValues(List<CheckBox> checkBoxes) {
        List<Integer> checkedValues = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                checkedValues.add(Integer.valueOf(checkBox.getParent().getId()));
            }
        }
        return checkedValues;
    }
    
    /**
     * Configures streaming provider options in the UI. Assigns IDs to streaming 
     * provider nodes, retrieves provider logos, and sets up click behavior for 
     * toggling provider selection.
     * 
     * @param streamers The GridPane containing UI elements for streaming providers.
     * @param mdc       The MovieDataController instance providing streaming provider data.
     */
    public void setProviders(GridPane streamers, MovieDataController mdc) {
       
        int index = 0;    
        for(Node spHbox : streamers.getChildren()) {
            spHbox.setId(Integer.toString(tmdbUtil.PROVIDER_IDS.get(index)));
            index++;
        }
        
        if(mdc.getStreamProviderMap() == null) {
            System.err.println("Could not load providers");
        }
        
        else{
            Set<Node> imageViews = streamers.lookupAll(".image-view");
            
            // Loop through each ImageView and set logos
            for (Node node : imageViews) {
                if (node instanceof ImageView imageView) {
                    CheckBox checkBox = ic.loadProviderLogos(imageView, mdc);

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
}
