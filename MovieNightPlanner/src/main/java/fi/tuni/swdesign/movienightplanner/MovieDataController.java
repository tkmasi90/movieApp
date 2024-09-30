/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

/**
 *
 * @author janii
 */
public class MovieDataController {
    
    // List of provider IDs that the app will support (e.g., Netflix, Amazon, Disney+, etc.)
    private final List<Integer> PROVIDER_IDS = List.of(337, 8, 119, 76, 323, 338, 2, 350, 2029, 1899);
    
    // A map to store the provider details after fetching them from the API.
    private Map<Integer, StreamingProvider> streamProviderMap;
    
    public PopularMoviesResponse getPopularMovies(){
        
        PopularMoviesResponse tempMovieList = null;

        String providers = (PROVIDER_IDS.stream()
            .map(String::valueOf)
            .collect(Collectors.joining("%20OR%20")));
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            String url = String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s", providers);
            SimpleHttpResponse response = makeHttpRequest(url);

            if (response != null) {
                Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

                tempMovieList = gson.fromJson(response.getBodyText(), PopularMoviesResponse.class);
            }
        } catch (Exception e){

            System.out.println(e);
        }
        
        // TODO:
        // This function doesn't need be called each time.
        // We can fetch this info only on first time and save to a local json
        if(streamProviderMap == null)
            fetchStreamingProviders();
        else {
            // TODO
        }
        
        // Add streaming provider information to the movies.
        addStreamingProviders(tempMovieList.getResults());
        
        return tempMovieList;
    }  
    
    private void fetchStreamingProviders() {

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            String url = "https://api.themoviedb.org/3/watch/providers/movie?language=en-US&watch_region=FI";
            SimpleHttpResponse response = makeHttpRequest(url);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Type streamingResponseType = new TypeToken<StreamingResponse>(){}.getType();
            StreamingResponse streamResponse = gson.fromJson(response.getBodyText(), streamingResponseType);
            
            // Filter the providers to include only the ones in PROVIDER_IDS.
            streamProviderMap = streamResponse.getResults()
                    .stream()
                    .filter(p -> PROVIDER_IDS.contains(p.provider_id))
                    .collect(Collectors.toMap(StreamingProvider::getProviderId, p -> p));       
        
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void addStreamingProviders(List<Movie> movieList) {
        for(var movie : movieList) {
            fetchMovieStreamProviders(movie);
        }
    }
    
    private void fetchMovieStreamProviders(Movie movie) {
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            String url = String.format("https://api.themoviedb.org/3/movie/%s/watch/providers", movie.getId());
            SimpleHttpResponse response = makeHttpRequest(url);

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
                        if(PROVIDER_IDS.contains(providerId)) {                        
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
    
    // Helper function to make HTTP requests
    private SimpleHttpResponse makeHttpRequest(String url) {
        SimpleHttpResponse response = null;
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            // Start the client
            httpclient.start();
            
            // Build the request
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();
            request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
            request.addHeader("accept", "application/json");
            
            // Execute the request and get the response
            Future<SimpleHttpResponse> future = httpclient.execute(request, null);
            response = future.get();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return response;
    }
}
