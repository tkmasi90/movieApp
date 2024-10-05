/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import fi.tuni.swdesign.movienightplanner.models.Movie;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import fi.tuni.swdesign.movienightplanner.models.StreamingProvider;
import fi.tuni.swdesign.movienightplanner.models.StreamingResponse;
import fi.tuni.swdesign.movienightplanner.utilities.HTTPTools;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

import java.util.stream.Collectors;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
/**
 *
 * @author janii
 */
public class MovieDataController {
    
    private final HTTPTools tools = new HTTPTools(); 
    
    // A map to store the provider details after fetching them from the API.
    private Map<Integer, StreamingProvider> streamProviderMap;
    
    public MoviesResponse getPopularMovies(){
        
        MoviesResponse tempMovieList = null;
        
        try {
            
            String url = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s", tools.getProviders());
            
            tempMovieList = (MoviesResponse)tools.makeGenericHttpRequest(url, MoviesResponse.class);
        
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }

        // Add streaming provider information to the movies.
        addStreamingProviders(tempMovieList.getResults());
        
        return tempMovieList;
    }

    public MoviesResponse getTopRatedMovies() {
        MoviesResponse tempMovieList = null;
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            String url = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&watch_region=FI&with_watch_monetization_types=flatrate&vote_count.gte=200&with_watch_providers=%s", tools.getProviders());
            SimpleHttpResponse response = tools.makeHttpRequest(url);

            Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

            tempMovieList = gson.fromJson(response.getBodyText(), MoviesResponse.class);

        } catch (Exception e){

            System.out.println(e);
        }
                
        // Add streaming provider information to the movies.
        addStreamingProviders(tempMovieList.getResults());

        return tempMovieList;
    }
    
    public void fetchStreamingProviders() {

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            String url = "https://api.themoviedb.org/3/watch/providers/movie?language=en-US&watch_region=FI";
            SimpleHttpResponse response = tools.makeHttpRequest(url);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Type streamingResponseType = new TypeToken<StreamingResponse>(){}.getType();
            StreamingResponse streamResponse = gson.fromJson(response.getBodyText(), streamingResponseType);
            
            // Filter the providers to include only the ones in PROVIDER_IDS.
            streamProviderMap = streamResponse.getResults()
                    .stream()
                    .filter(p -> tools.PROVIDER_IDS.contains(p.provider_id))
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
    
    private void fetchMovieStreamProviders(Movie movie) {
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            String url = String.format("https://api.themoviedb.org/3/movie/%s/watch/providers", movie.getId());
            SimpleHttpResponse response = tools.makeHttpRequest(url);

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.getBodyText(), JsonObject.class);
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
                        if(tools.PROVIDER_IDS.contains(providerId)) {                        
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
