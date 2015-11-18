package com.unit7.gis.api.model;

/**
 * Created by breezzo on 18.11.15.
 */
public interface ApiResponse {
    String getResponseCode();
    String getErrorCode();
    String getErrorMessage();
}
