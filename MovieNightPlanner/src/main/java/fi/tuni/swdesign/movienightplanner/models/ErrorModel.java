/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

/**
 * Helper class for TMDB-related error handling.
 *
 * @author janii
 */
public class ErrorModel {

    @Expose
    public int status_code;
    @Expose
    public boolean success;
    @Expose
    public String status_message;

    /**
     * Constructs an ErrorModel with the specified status code, success flag,
     * and status message.
     *
     * @param status_code the status code of the error
     * @param success the success flag indicating whether the operation was
     * successful
     * @param status_message the status message describing the error
     */
    public ErrorModel(int status_code, boolean success, String status_message) {
        this.status_code = status_code;
        this.success = success;
        this.status_message = status_message;
    }

    /**
     * Gets the status code of the error.
     *
     * @return the status code
     */
    public int getStatus_code() {
        return status_code;
    }

    /**
     * Sets the status code of the error.
     *
     * @param status_code the status code to set
     */
    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    /**
     * Checks if the operation was successful.
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success flag indicating whether the operation was successful.
     *
     * @param success the success flag to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the status message describing the error.
     *
     * @return the status message
     */
    public String getStatus_message() {
        return status_message;
    }

    /**
     * Sets the status message describing the error.
     *
     * @param status_message the status message to set
     */
    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

}
