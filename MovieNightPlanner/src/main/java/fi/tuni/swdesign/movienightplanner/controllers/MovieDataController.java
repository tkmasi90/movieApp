/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.tuni.swdesign.movienightplanner.models.Credits;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MovieDetails;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.models.StreamingResponse;
import fi.tuni.swdesign.movienightplanner.utilities.GSONTools;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;
import java.io.IOException;

import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import org.apache.hc.client5.http.HttpResponseException;
/**
 *
 * @author janii
 */
public class MovieDataController {
    
    private final HTTPTools httpTools = new HTTPTools();
    private final GSONTools gsonTools = new GSONTools();
    
    // A map to store the provider details after fetching them from the API.
    private Map<Integer, StreamingProvider> streamProviderMap;
    
    public MoviesResponse fetchMoviesResponse(String url) throws HttpResponseException{
        MoviesResponse tempMovieList = null;
        
        try {
            String httpResponseString = httpTools.makeGenericHttpRequest(url);
            tempMovieList = (MoviesResponse) gsonTools.convertJSONToObjects(httpResponseString, MoviesResponse.class);
        
        } catch (HttpResponseException ex){
            throw new HttpResponseException(ex.getStatusCode(),ex.getReasonPhrase());
        }
        
        catch (IOException | InterruptedException e) {
            System.out.println(e);
        }

        // Add streaming provider information to the movies.
        addStreamingProviders(tempMovieList.getResults());
        
        return tempMovieList;
    }
    
    public void fetchStreamingProviders() {

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            
            String url = "https://api.themoviedb.org/3/watch/providers/movie?language=en-US&watch_region=FI";
            String httpResponseString = httpTools.makeGenericHttpRequest(url);
            StreamingResponse streamResponse = (StreamingResponse) gsonTools.convertJSONToObjects(httpResponseString, StreamingResponse.class);
            
            // Filter the providers to include only the ones in PROVIDER_IDS.
            streamProviderMap = streamResponse.getResults()
                    .stream()
                    .filter(p -> httpTools.PROVIDER_IDS.contains(p.provider_id))
                    .collect(Collectors.toMap(StreamingProvider::getProviderId, p -> p));       
        
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void addStreamingProviders(List<Movie> movieList) {
        for(Movie movie : movieList) {
            fetchMovieStreamProviders(movie);
        }
    }

    public void fetchMovieDetails(Movie movie) {
        try {
            String url = String.format("https://api.themoviedb.org/3/movie/%s?language=en-US", movie.getId());
            SimpleHttpResponse response = (SimpleHttpResponse) httpTools.makeGenericHttpRequest(url);
            MovieDetails movieDetails = (MovieDetails) gsonTools.convertJSONToObjects(response.getBodyText(), MovieDetails.class);
            movie.setMovieDetails(movieDetails);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    public void fetchMovieCredits(Movie movie) {
        try {
            String url = String.format("https://api.themoviedb.org/3/movie/%s/credits", movie.getId());
            SimpleHttpResponse response = (SimpleHttpResponse) httpTools.makeGenericHttpRequest(url);
            Credits credits = (Credits) gsonTools.convertJSONToObjects(response.getBodyText(), Credits.class);
            movie.setCredits(credits);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
    
    private void fetchMovieStreamProviders(Movie movie) {
        
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
                        if(httpTools.PROVIDER_IDS.contains(providerId)) {                        
                            movie.addStreamingProvider(streamProviderMap.get(providerId));
                        }
                    }
                }
            } else {
            System.out.println("No data found for county code FI.");
            }
                   
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Map<Integer, StreamingProvider> getStreamProviderMap() {
        return streamProviderMap;
    }
}
