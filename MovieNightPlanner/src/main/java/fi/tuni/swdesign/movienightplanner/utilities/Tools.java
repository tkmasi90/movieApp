/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

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
 *
 * @author Make
 */
public class Tools {

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
    
    // Helper function to make HTTP requests
    public SimpleHttpResponse makeHttpRequest(String url) {
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
    
    public String getProviders() {
        try {
            return URLEncoder.encode((PROVIDER_IDS.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"))), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
}
