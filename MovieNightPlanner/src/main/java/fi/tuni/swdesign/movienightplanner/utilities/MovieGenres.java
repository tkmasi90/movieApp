package fi.tuni.swdesign.movienightplanner.utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing movie genres.
 */
public class MovieGenres {
    private static final Map<Integer, String> GENRES_BY_ID;
    private static final Map<String, Integer> GENRES_BY_NAME;

    static {
        Map<Integer, String> genresById = new HashMap<>();
        Map<String, Integer> genresByName = new HashMap<>();

        genresById.put(28, "Action");
        genresById.put(12, "Adventure");
        genresById.put(16, "Animation");
        genresById.put(35, "Comedy");
        genresById.put(80, "Crime");
        genresById.put(99, "Documentary");
        genresById.put(18, "Drama");
        genresById.put(10751, "Family");
        genresById.put(14, "Fantasy");
        genresById.put(36, "History");
        genresById.put(27, "Horror");
        genresById.put(10402, "Music");
        genresById.put(9648, "Mystery");
        genresById.put(10749, "Romance");
        genresById.put(878, "Science Fiction");
        genresById.put(10770, "TV Movie");
        genresById.put(53, "Thriller");
        genresById.put(10752, "War");
        genresById.put(37, "Western");

        for (Map.Entry<Integer, String> entry : genresById.entrySet()) {
            genresByName.put(entry.getValue(), entry.getKey());
        }

        GENRES_BY_ID = Collections.unmodifiableMap(genresById);
        GENRES_BY_NAME = Collections.unmodifiableMap(genresByName);
    }

    /**
     * Gets the genre name by ID.
     *
     * @param id the genre ID
     * @return the genre name, or null if not found
     */
    public static String getGenreNameById(int id) {
        return GENRES_BY_ID.get(id);
    }

    /**
     * Gets the genre ID by name.
     *
     * @param name the genre name
     * @return the genre ID, or null if not found
     */
    public static Integer getGenreIdByName(String name) {
        return GENRES_BY_NAME.get(name);
    }

    /**
     * Gets all genres as a map of ID to name.
     *
     * @return an unmodifiable map of genre IDs to names
     */
    public static Map<Integer, String> getAllGenresById() {
        return GENRES_BY_ID;
    }

    /**
     * Gets all genres as a map of name to ID.
     *
     * @return an unmodifiable map of genre names to IDs
     */
    public static Map<String, Integer> getAllGenresByName() {
        return GENRES_BY_NAME;
    }
}