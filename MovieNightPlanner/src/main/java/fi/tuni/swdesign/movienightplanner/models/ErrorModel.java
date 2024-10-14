/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

/**
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

    // Constructor
    public ErrorModel(int status_code, boolean success, String status_message) {
        this.status_code = status_code;
        this.success = success;
        this.status_message = status_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }




}
