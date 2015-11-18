package com.unit7.gis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Ответ от апи с описанием филиала
 *
 * Created by breezzo on 16.11.15.
 */
public class BranchProfileDescription extends RestAreaDescription implements ApiResponse {
    @SerializedName("api_version")
    private String apiVersion;
    @SerializedName("response_code")
    private String responseCode;
    @SerializedName("city_name")
    private String cityName;

    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
