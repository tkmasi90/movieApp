/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

/**
 * A helper class to facilitate all HTTP-operations
 *
 * @author Make, janii
 */
public class HTTPTools {

    
    /**
    * Handles HTTP requests and responses asynchronously.
    *
    * @param url  The URL string for the HTTP request.
    * @return SimpleHttpResponse
    * @throws IOException            If an I/O error occurs.
    * @throws InterruptedException   If the operation is interrupted.
    * 
    * @author janii
    */
    public Object makeGenericHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException {
        
        SimpleHttpResponse httpResponse = null;
               
        
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {

            httpClient.start();
            
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();
            // TODO: Switch to API key and login. 
            request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
            request.addHeader("accept", "application/json");
            
            Future<SimpleHttpResponse> future = httpClient.execute(request, null);
            
            httpResponse = future.get();
                
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // TODO: Error handling (All relevant HTTP error codes...)
        if (httpResponse == null || httpResponse.getBodyText() == null) {
            throw new IllegalStateException("The HTTP response or the response body is null");
        }
        
        return httpResponse;
    }
    
}
