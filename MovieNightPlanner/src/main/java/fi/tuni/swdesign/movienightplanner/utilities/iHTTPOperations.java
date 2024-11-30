/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.utilities;

import java.io.IOException;

/**
 * Interface with methods to implement HTTP-operations to fetch data from
 * movie data -related services.
 *
 * @author janii
 */
public interface iHTTPOperations {

    /**
    * Handles TMDB-HTTP requests and responses asynchronously.
    *
    * @param url  The URL string for the HTTP request.
    * @return SimpleHttpResponse
    * @throws IOException            If an I/O error occurs.
    * @throws InterruptedException   If the operation is interrupted.
    * @throws IllegalStateException  If the operation failed at the server.
    */
    public String makeGenericHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException;
    
        /**
    * Handles HTTP requests and responses asynchronously. Only works with The Movie of the Night due to the mandatory headers.
    *
    * @param url  The URL string for the HTTP request.
    * @return SimpleHttpResponse
    * @throws IOException            If an I/O error occurs.
    * @throws InterruptedException   If the operation is interrupted.
    * @throws IllegalStateException  If the operation failed at the server.
    */
    public String makeTMOTNHttpRequest(String url) throws IOException, InterruptedException, IllegalStateException;
    
    
}
