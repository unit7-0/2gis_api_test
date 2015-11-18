package com.unit7.gis.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Ответ от апи с результатом поиска
 *
 * Created by breezzo on 16.11.15.
 */
public class BranchSearchResponse implements ApiResponse {
    @SerializedName("api_version")
    private String apiVersion;
    @SerializedName("response_code")
    private String responseCode;
    private String what;
    private String where;
    private int total;
    private List<Branch> result;

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

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Branch> getResult() {
        return result;
    }

    public void setResult(List<Branch> result) {
        this.result = result;
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
