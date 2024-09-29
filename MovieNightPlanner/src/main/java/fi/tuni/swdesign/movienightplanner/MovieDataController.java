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

        // https://hc.apache.org/httpcomponents-client-5.3.x/quickstart.html
    
        // Create a string of provider IDs formatted for the query (joined by %20OR%20)
        SimpleHttpResponse response1;
        String providers = (PROVIDER_IDS.stream()
            .map(String::valueOf)
            .collect(Collectors.joining("%20OR%20")));
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            // Start the client
            httpclient.start();

            // Execute request
            SimpleHttpRequest request1 = SimpleRequestBuilder
                    .get(String.format("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI&with_watch_monetization_types=flatrate&with_watch_providers=%s", providers))
                    .build();
            request1.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZDAyZjc4ODU0Njc3MTZiYTkyY2Y4YzNjNDY1MTY3OCIsIm5iZiI6MTcyNjczNDgwNS41MzM0MjksInN1YiI6IjY2ZWJlMDgxNjJjNGJiMThjOTc0OGE1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cs2Z9pMRYHWmTjnJbeEpLERWzVPHKBsbkJWmwG1Md2o");
            request1.addHeader("accept", "application/json");

            Future<SimpleHttpResponse> future = httpclient.execute(request1, null);
            // and wait until response is received
            response1 = future.get();
            System.out.println(request1.getRequestUri() + "->" + response1.getCode());

            Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

            tempMovieList = gson.fromJson(response1.getBodyText(), PopularMoviesResponse.class);
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
        SimpleHttpResponse response1;

        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            httpclient.start();
            SimpleHttpRequest request1 = SimpleRequestBuilder.get("https://api.themoviedb.org/3/watch/providers/movie?language=en-US&watch_region=FI").build();
            request1.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
            request1.addHeader("accept", "application/json");
            Future<SimpleHttpResponse> future = httpclient.execute(request1, null);
            response1 = future.get();
            System.out.println(request1.getRequestUri() + "->" + response1.getCode());

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Type streamingResponseType = new TypeToken<StreamingResponse>(){}.getType();
            StreamingResponse response = gson.fromJson(response1.getBodyText(), streamingResponseType);
            
            // Filter the providers to include only the ones in PROVIDER_IDS.
            streamProviderMap = response.getResults()
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
        SimpleHttpResponse response1;
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            httpclient.start();

            // Execute request
            SimpleHttpRequest request1 = SimpleRequestBuilder.get(String.format("https://api.themoviedb.org/3/movie/%s/watch/providers", movie.getId())).build();
            request1.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
            request1.addHeader("accept", "application/json");

            Future<SimpleHttpResponse> future = httpclient.execute(request1, null);
            // and wait until response is received
            response1 = future.get();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response1.getBodyText(), JsonObject.class);
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
}
