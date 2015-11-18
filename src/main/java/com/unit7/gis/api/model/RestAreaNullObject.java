package com.unit7.gis.api.model;

/**
 * Special case
 *
 * Created by breezzo on 18.11.15.
 */
public class RestAreaNullObject extends RestAreaDescription {
    public static RestAreaNullObject INSTANCE = new RestAreaNullObject();

    private RestAreaNullObject() {
    }
}
