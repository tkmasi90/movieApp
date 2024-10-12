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
    public int code;
    @Expose
    public int httpStatus;
    @Expose
    public String message;

    // Constructor
    public ErrorModel(int code, int httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                '}';
    }


}
