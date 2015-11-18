package com.unit7.gis.api.client;

import com.google.common.base.Strings;
import com.unit7.gis.api.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by breezzo on 18.11.15.
 */
public class ApiErrorHandler {

    public static final String API_SUCCESS_RESPONSE_CODE = "200";
    public static final String DEFAULT_ERROR_MESSAGE = "Произошла ошибка. Обратитесь к администратору";

    private static final Logger logger = LoggerFactory.getLogger(ApiErrorHandler.class);

    public void handleIfError(ApiResponse response) {
        if (!Strings.isNullOrEmpty(response.getErrorCode())) {
            logger.error("API errorCode: {}, errorMessage: {}",
                    response.getErrorCode(), response.getErrorMessage());
            throw new RuntimeException(DEFAULT_ERROR_MESSAGE);
        }

        if (!API_SUCCESS_RESPONSE_CODE.equals(response.getResponseCode())) {
            logger.error("API sent response code: {}", response.getResponseCode());
            throw new RuntimeException(DEFAULT_ERROR_MESSAGE);
        }
    }
}
