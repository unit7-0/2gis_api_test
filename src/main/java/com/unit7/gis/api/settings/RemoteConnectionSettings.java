package com.unit7.gis.api.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Настройки клиента для связи с апи
 *
 * Created by breezzo on 17.11.15.
 */
public final class RemoteConnectionSettings {

    public static final int DEFAULT_MAX_CONNECTIONS = 20;
    public static final int DEFAULT_TIMEOUT_MS = 15000;

    public final int maxConnections;
    public final int connectionTimeoutMs;

    private static final Logger logger = LoggerFactory.getLogger(RemoteConnectionSettings.class);
    private static RemoteConnectionSettings instance = load();

    public RemoteConnectionSettings(int maxConnections, int connectionTimeoutMs) {
        this.maxConnections = maxConnections;
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public static RemoteConnectionSettings get() {
        return instance;
    }

    private static RemoteConnectionSettings load() {

        int maxConnections;
        int connectionTimeoutMs;

        try (InputStream stream = RemoteConnectionSettings.class.getResourceAsStream("/remote_connection.properties")) {
            Properties props = new Properties();
            props.load(stream);

            maxConnections = Integer.parseInt(
                    props.getProperty("maxConnections", Integer.toString(DEFAULT_MAX_CONNECTIONS)));
            connectionTimeoutMs = Integer.parseInt(
                    props.getProperty("connectionTimeoutMs", Integer.toString(DEFAULT_TIMEOUT_MS)));

        } catch (Exception e) {
            logger.error("Could not load connection settings", e);
            
            maxConnections = DEFAULT_MAX_CONNECTIONS;
            connectionTimeoutMs = DEFAULT_TIMEOUT_MS;
        }

        return new RemoteConnectionSettings(maxConnections, connectionTimeoutMs);
    }
}
