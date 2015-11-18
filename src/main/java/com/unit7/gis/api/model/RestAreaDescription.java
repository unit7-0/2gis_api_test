package com.unit7.gis.api.model;

/**
 * Ответ от сервиса с описанием найденного филиала
 *
 * Created by breezzo on 16.11.15.
 */
public class RestAreaDescription {
    private String name;
    private String address;
    private double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
