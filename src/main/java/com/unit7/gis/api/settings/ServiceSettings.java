package com.unit7.gis.api.settings;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

/**
 * Общие настройки сервиса
 *
 * Created by breezzo on 17.11.15.
 */
public final class ServiceSettings {

    public static final String DEFAULT_API_VERSION = "1.3";

    public final String apiKey;
    public final String apiVersion;
    public final List<String> cityNames;

    private static final ServiceSettings instance = load();

    private ServiceSettings(String apiKey, String apiVersion, List<String> cityNames) {
        this.apiKey = apiKey;
        this.apiVersion = apiVersion;
        this.cityNames = cityNames;
    }

    public static ServiceSettings get() {
        return instance;
    }

    private static ServiceSettings load() {
        String apiKey;
        String apiVersion;
        List<String> cityNames;

        try (InputStream stream = ServiceSettings.class.getResourceAsStream("/service.properties")) {
            Properties props = new Properties();
            props.load(new InputStreamReader(stream, Charset.forName("UTF-8")));

            apiKey = props.getProperty("apiKey");
            apiVersion = props.getProperty("apiVersion", DEFAULT_API_VERSION);

            String rawCityNames = props.getProperty("cityNames");

            cityNames = ImmutableList.copyOf(rawCityNames.split(","));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return new ServiceSettings(apiKey, apiVersion, cityNames);
    }
}
