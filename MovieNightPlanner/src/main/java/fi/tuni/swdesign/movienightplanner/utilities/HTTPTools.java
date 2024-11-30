/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import fi.tuni.swdesign.movienightplanner.models.ErrorModel;

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

/**
 * A helper class to facilitate all HTTP-operations.
 * 
 * Separate clients for different services.
 *
 * @author Make, janii
 */
public class HTTPTools implements iHTTPOperations {

    
    /**
    * Handles HTTP requests and responses asynchronously.
    *
    * @param url  The URL string for the HTTP request.
    * @return SimpleHttpResponse
    * @throws IOException            If an I/O error occurs.
    * @throws InterruptedException   If the operation is interrupted.
    * @throws IllegalStateException  If the operation failed at the server.
    */
    @Override
    public String makeGenericHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException {
        
        SimpleHttpResponse httpResponse = null;
                      
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {

            httpClient.start();
            
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();
            // TODO: Switch to API key and login. 
            request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZDAyZjc4ODU0Njc3MTZiYTkyY2Y4YzNjNDY1MTY3OCIsIm5iZiI6MTczMjA5MjQ2OS4xMDgwMzU4LCJzdWIiOiI2NmViZTA4MTYyYzRiYjE4Yzk3NDhhNTciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.j_6p9XYkd-a94uYFKhUv6dW8GTT0OlHBkhfuuaS8CAM");
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
            
            throw new HttpResponseException(httpResponse.getCode(), newError.getStatus_message());
        }
        
        return httpResponse.getBodyText();
    }
    
    /**
    * Handles HTTP requests and responses asynchronously. Only works with The Movie of the Night due to the mandatory headers.
    *
    * @param url  The URL string for the HTTP request.
    * @return SimpleHttpResponse
    * @throws IOException            If an I/O error occurs.
    * @throws InterruptedException   If the operation is interrupted.
    * @throws IllegalStateException  If the operation failed at the server.
    */
    @Override
    public String makeTMOTNHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException {
        
        SimpleHttpResponse httpResponse = null;
               
        
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {

            httpClient.start();
            
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();
            // TODO: Switch to API key and login. 
            request.addHeader("accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("x-rapidapi-key", "b3c77db2b4msh53577b5dd8aab2dp1f6a22jsn1bae23963328");
            request.addHeader("x-rapidapi-host", "streaming-availability.p.rapidapi.com");
            
            Future<SimpleHttpResponse> future = httpClient.execute(request, null);
            
            httpResponse = future.get();
                
        } catch (Exception e) {
            System.out.println("EX: " + e);
        }
        
        // TODO: Error handling (All relevant HTTP error codes...)
        if (httpResponse == null || httpResponse.getBodyText() == null) {
            throw new IllegalStateException("The HTTP response or the response body is null");
        }
        
        if (httpResponse.getCode() != 200)
        {
            
            GSONTools gsonTools = new GSONTools();
            
            ErrorModel newError = (ErrorModel)gsonTools.convertJSONToObjects(httpResponse.getBodyText(), ErrorModel.class);
            
            throw new HttpResponseException(httpResponse.getCode(), newError.getStatus_message());
        }
        
        return httpResponse.getBodyText();
    }
    
}
