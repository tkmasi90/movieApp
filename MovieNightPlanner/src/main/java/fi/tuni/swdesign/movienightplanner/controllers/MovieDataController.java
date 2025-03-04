/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import fi.tuni.swdesign.movienightplanner.models.GenresResponse;
import fi.tuni.swdesign.movienightplanner.models.Credits;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MovieDetails;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.models.SpokenLanguage;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.models.StreamingResponse;
import fi.tuni.swdesign.movienightplanner.utilities.TMDbUtility;
import fi.tuni.swdesign.movienightplanner.utilities.GSONTools;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;
import fi.tuni.swdesign.movienightplanner.utilities.LanguageCodes;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.HttpResponseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

/**
 * Controller that handles all movie data related application logic.
 *
 * @author janii, Make, kian
 */
public class MovieDataController {

    private final HTTPTools httpTools = new HTTPTools();
    private final GSONTools gsonTools = new GSONTools();
    private final TMDbUtility tmdbUtil = new TMDbUtility();

    // A map to store the provider details after fetching them from the API.
    private Map<Integer, StreamingProvider> streamProviderMap;

    /**
     * Fetches the movies response from the given URL.
     *
     * @param url the URL to fetch the movies response from
     * @return the MoviesResponse object containing the list of movies
     * @throws HttpResponseException if there is an error in the HTTP response
     */
    public MoviesResponse fetchMoviesResponse(String url) throws HttpResponseException {
        MoviesResponse tempMovieList = null;

        try {
            String httpResponseString = httpTools.makeGenericHttpRequest(url);
            tempMovieList = (MoviesResponse) gsonTools.convertJSONToObjects(httpResponseString, MoviesResponse.class);
            filterMissingDates(tempMovieList);

            addStreamingProviders(tempMovieList.getResults());

        } catch (HttpResponseException ex) {
            throw new HttpResponseException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }

        return tempMovieList;
    }

    /**
     * Fetches the streaming providers and filters them based on predefined
     * provider IDs.
     *
     * @throws HttpResponseException if there is an error in the HTTP response
     */
    public void fetchStreamingProviders() throws HttpResponseException {

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {

            String url = "https://api.themoviedb.org/3/watch/providers/movie?language=en-US&watch_region=FI";
            String httpResponseString = httpTools.makeGenericHttpRequest(url);
            StreamingResponse streamResponse = (StreamingResponse) gsonTools.convertJSONToObjects(httpResponseString, StreamingResponse.class);

            // Filter the providers to include only the ones in PROVIDER_IDS.
            streamProviderMap = streamResponse.getResults()
                    .stream()
                    .filter(p -> tmdbUtil.PROVIDER_IDS.contains(p.getProviderId()))
                    .collect(Collectors.toMap(StreamingProvider::getProviderId, p -> p));

        } catch (HttpResponseException ex) {
            throw new HttpResponseException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Adds streaming provider information to the list of movies.
     *
     * @param movieList the list of movies to add streaming provider information
     * to
     * @throws HttpResponseException if there is an error in fetching the
     * streaming providers
     */
    private void addStreamingProviders(List<Movie> movieList) throws HttpResponseException {
        for (Movie movie : movieList) {
            fetchMovieStreamProviders(movie);
        }
    }

    /**
     * Fetches detailed information for a given movie and sets it in the movie
     * object.
     *
     * @param movie the movie object to fetch details for
     */
    public void fetchMovieDetails(Movie movie) {
        try {
            String url = String.format("https://api.themoviedb.org/3/movie/%s?language=en-US", movie.getId());
            String responseBody = httpTools.makeGenericHttpRequest(url);
            MovieDetails movieDetails = (MovieDetails) gsonTools.convertJSONToObjects(responseBody, MovieDetails.class);
            movie.setMovieDetails(movieDetails);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Fetches the credits for a given movie and sets them in the movie object.
     *
     * @param movie the movie object to fetch credits for
     */
    public void fetchMovieCredits(Movie movie) {
        try {
            String url = String.format("https://api.themoviedb.org/3/movie/%s/credits", movie.getId());
            String responseBody = httpTools.makeGenericHttpRequest(url);
            Credits credits = (Credits) gsonTools.convertJSONToObjects(responseBody, Credits.class);
            movie.setCredits(credits);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Fetches streaming provider information for a given movie and adds it to
     * the movie object.
     *
     * @param movie the movie object to fetch streaming providers for
     * @throws HttpResponseException if there is an error in the HTTP response
     */
    private void fetchMovieStreamProviders(Movie movie) throws HttpResponseException {

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {

            String url = String.format("https://api.themoviedb.org/3/movie/%s/watch/providers", movie.getId());
            String httpResponseString = httpTools.makeGenericHttpRequest(url);

            JsonObject jsonObject = gsonTools.getGson().fromJson(httpResponseString, JsonObject.class);
            JsonObject results = jsonObject.getAsJsonObject("results");

            // Check if there is data for the "FI" region.
            if (results.has("FI")) {
                JsonObject fiData = results.getAsJsonObject("FI");

                // Retrieve the flatrate array
                if (fiData.has("flatrate")) {
                    JsonArray flatrateArray = fiData.getAsJsonArray("flatrate");

                    // Iterate through the providers and print their details
                    for (JsonElement providerElement : flatrateArray) {
                        JsonObject providerObj = providerElement.getAsJsonObject();
                        int providerId = providerObj.get("provider_id").getAsInt();

                        // Only add providers from the predefined PROVIDER_IDS list.
                        if (tmdbUtil.PROVIDER_IDS.contains(providerId)) {
                            movie.addStreamingProvider(streamProviderMap.get(providerId));
                        }
                    }
                }

                if (fiData.has("free")) {
                    JsonArray flatrateArray = fiData.getAsJsonArray("free");

                    // Iterate through the providers and print their details
                    for (JsonElement providerElement : flatrateArray) {
                        JsonObject providerObj = providerElement.getAsJsonObject();
                        int providerId = providerObj.get("provider_id").getAsInt();

                        // Check if provider is YLE areena
                        if (providerId == 323) {
                            movie.addStreamingProvider(streamProviderMap.get(providerId));
                        }
                    }
                }

            }

        } catch (HttpResponseException ex) {
            throw new HttpResponseException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Fetches the genres from the given URL.
     *
     * @param url the URL to fetch the genres from
     * @return the GenresResponse object containing the list of genres
     * @throws HttpResponseException if there is an error in the HTTP response
     */
    public GenresResponse fetchGenres(String url) throws HttpResponseException {

        GenresResponse responseObject = null;

        try {
            String responseBody = httpTools.makeGenericHttpRequest(url);

            responseObject = (GenresResponse) gsonTools.convertJSONToObjects(responseBody, GenresResponse.class);

        } catch (HttpResponseException ex) {
            throw new HttpResponseException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        return responseObject;
    }

    /**
     * Fetches the list of spoken languages from the given URL and filters them
     * based on predefined languages.
     *
     * @param url the URL to fetch the spoken languages from
     * @return the list of filtered spoken languages
     * @throws HttpResponseException if there is an error in the HTTP response
     */
    public List<SpokenLanguage> fetchSpokenLanguages(String url) throws HttpResponseException {

        List<SpokenLanguage> responseList = null;

        try {
            String responseBody = httpTools.makeGenericHttpRequest(url);

            // Parse the response as a List of SpokenLanguage
            Type listType = new TypeToken<List<SpokenLanguage>>() {
            }.getType();
            responseList = gsonTools.convertJSONToObjects(responseBody, listType);

        } catch (HttpResponseException ex) {
            throw new HttpResponseException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }

        if (responseList != null) {
            var languagesUsed = LanguageCodes.getAllCountryCodes();

            responseList = responseList.stream()
                    .filter(lang -> languagesUsed.contains(lang.getIso639_1()))
                    .collect(Collectors.toList());
        }

        return responseList;
    }

    /**
     * Gets the map of streaming providers.
     *
     * @return the map of streaming providers with their IDs as keys
     */
    public Map<Integer, StreamingProvider> getStreamProviderMap() {
        return streamProviderMap;
    }

    private void filterMissingDates(MoviesResponse movieResponse) {
        List<Movie> filteredMovies = movieResponse.getResults().stream().filter(movie -> {
            return (!"".equals(movie.getReleaseDate()));
        }).collect(Collectors.toList());
       
        movieResponse.setResults(filteredMovies);
    }
}
