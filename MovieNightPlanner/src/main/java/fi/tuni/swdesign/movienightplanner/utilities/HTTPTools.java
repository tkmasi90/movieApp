/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.tuni.swdesign.movienightplanner.models.ErrorModel;
import fi.tuni.swdesign.movienightplanner.models.MoviesResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.hc.client5.http.HttpResponseException;
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

    // List of providers that the app supports at the moment
    private static final class PROVIDERS {
        public static final int NETFLIX = 8;
        public static final int DISNEY = 337;
        public static final int AMAZON = 119;
        public static final int VIAPLAY = 76;
        public static final int AREENA = 323;
        public static final int RUUTU = 338;
        public static final int APPLE = 2;
        public static final int SKY = 1773;
        public static final int MTV = 2029;
        public static final int MAX = 1899;
    }
    // Method to get provider IDs dynamically using reflection
    private List<Integer> getProviderIds() {
        List<Integer> providerIds = new ArrayList<>();
        Field[] fields = PROVIDERS.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == int.class) {
                try {
                    providerIds.add(field.getInt(null)); // Get the value of the static field
                } catch (IllegalAccessException e) {
                    System.out.println("Unable to access field: " + field.getName());
                }
            }
        }
        return providerIds;
    }
    
    public final List<Integer> PROVIDER_IDS = getProviderIds();

    
    public String getProviders() {
        try {
            return URLEncoder.encode((PROVIDER_IDS.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    
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
    public String makeGenericHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException {
        
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
        
        if (httpResponse.getCode() != 200)
        {
            GSONTools gsonTools = new GSONTools();
            
            ErrorModel newError = (ErrorModel)gsonTools.convertJSONToObjects(httpResponse.getBodyText(), ErrorModel.class);
            
            throw new HttpResponseException(httpResponse.getCode(), newError.getMessage());
        }
        
        return httpResponse.getBodyText();
    }
    
}
