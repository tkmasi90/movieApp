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
public class HTTPTools {

    
    /**
    * Handles HTTP requests and responses asynchronously.
    *
    * @param url  The URL string for the HTTP request.
    * @return SimpleHttpResponse
    * @throws IOException            If an I/O error occurs.
    * @throws InterruptedException   If the operation is interrupted.
    * @throws IllegalStateException  If the operation failed at the server.
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
    * 
    * @author janii
    */
    public String makeTMOTNHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException {
        
        SimpleHttpResponse httpResponse = null;
               
        
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {

            httpClient.start();
            
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();
            // TODO: Switch to API key and login. 
            request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDc4ZTFkOGE4ZDkyOTc3ODhkZmJlM2U4ZjFmODI2MiIsIm5iZiI6MTcyNjY0NzQxMy4zMTU5MzUsInN1YiI6IjY2ZWE4YjQ0NTE2OGE4OTZlMTFmNDkxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-Nh_jobP1QsTwiihO2YVhrRTuaX89mle0qVx_nKxZEs");
            request.addHeader("accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("x-rapidapi-ua", "RapidAPI-Playground");
            request.addHeader("x-rapidapi-key", "828f0857d0msh8bd2546014cdc05p18e636jsn76036c21d578");
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
