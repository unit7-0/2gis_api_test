package com.unit7.gis.api.client;

import com.unit7.gis.api.settings.RemoteConnectionSettings;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Optional;

/**
 * HttpClient one multithreaded instance wrapper
 * <br/>
 * Threadsafe
 * <br/>
 * Created by breezzo on 17.11.15.
 */
public class HttpClientInst {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientInst.class);
    private static HttpClientInst instance;

    private CloseableHttpClient httpClient;

    private HttpClientInst() {

        logger.info("http client initialization...");

        HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(RemoteConnectionSettings.get().connectionTimeoutMs)
                .setSocketTimeout(RemoteConnectionSettings.get().connectionTimeoutMs)
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setMaxConnTotal(RemoteConnectionSettings.get().maxConnections)
                .setDefaultRequestConfig(requestConfig)
                .build();

        logger.info("http client initialized");
    }

    public static HttpClientInst instance() {
        HttpClientInst local = instance;
        if (local == null) {
            synchronized (HttpClientInst.class) {
                local = instance;
                if (local == null) {
                    instance = local = new HttpClientInst();
                }
            }
        }

        return local;
    }

    /**
     *
     * @return response optional content if present, otherwise empty optional
     * @throws RuntimeException if any error occurred
     */
    @Nonnull
    public Optional<byte[]> executeGetRequest(URI uri) {
        return execute(new HttpGet(uri));
    }

    private Optional<byte[]> execute(HttpUriRequest request) {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            return entity == null ? Optional.empty() :
                    Optional.ofNullable(IOUtils.toByteArray(entity.getContent()));
        } catch (Exception e) {
            logger.error("Error occurred when executing request: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
