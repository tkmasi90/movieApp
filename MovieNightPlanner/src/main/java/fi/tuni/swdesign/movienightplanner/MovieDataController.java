/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
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
    List<Integer> PROVIDER_IDS = Arrays.asList(337, 8, 119, 76, 323, 338, 2, 350, 2029, 1899);
    
    public PopularMoviesResponse getPopularMovies(){
        
        PopularMoviesResponse tempMovieList = null;
        
        // https://hc.apache.org/httpcomponents-client-5.3.x/quickstart.html
    
        SimpleHttpResponse response1;
        
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            // Start the client
            httpclient.start();

            // Execute request
            SimpleHttpRequest request1 = SimpleRequestBuilder.get("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&watch_region=FI").build();
            request1.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
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
        fetchStreamingProviders();
        
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
            // and wait until response is received
            response1 = future.get();
            System.out.println(request1.getRequestUri() + "->" + response1.getCode());

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Type streamingResponseType = new TypeToken<StreamingResponse>(){}.getType();
            StreamingResponse response = gson.fromJson(response1.getBodyText(), streamingResponseType);
            
            List<StreamingProvider> providers = response.getResults()
                    .stream()
                    .filter(p -> PROVIDER_IDS.contains(p.provider_id))
                    .collect(Collectors.toList());
            
                for (StreamingProvider provider : providers) {
                    System.out.println(provider.provider_name + " " + provider.provider_id);
            }
        
        
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
