package com.unit7.gis.api.rest;

import com.unit7.gis.api.client.ApiClient;
import com.unit7.gis.api.client.ApiClientImpl;
import com.unit7.gis.api.model.RestAreaDescription;
import com.unit7.gis.api.model.RestAreaNullObject;
import com.unit7.gis.api.settings.ServiceSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by breezzo on 15.11.15.
 */
@Path("rest-area/{kind}")
public class RestAreaService {

    private static final Logger logger = LoggerFactory.getLogger(RestAreaService.class);

    private ApiClient apiClient = new ApiClientImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response findBestPlace(@PathParam("kind") String restAreaKind) {

        List<String> cities = ServiceSettings.get().cityNames;
        List<RestAreaDescription> finalResponse = null;

        try {
             finalResponse =
                    cities.stream()
                            .map(city ->
                                    CompletableFuture.supplyAsync(
                                            () -> apiClient.findBestBranch(restAreaKind, city))
                            )
                            .map(future ->
                                    future.thenApplyAsync(
                                            branch -> {
                                                if (branch.isPresent()) {
                                                    return apiClient.completeBranch(branch.get(), restAreaKind);
                                                }

                                                return RestAreaNullObject.INSTANCE;
                                            })
                            )
                            .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    throw new RuntimeException(e.getMessage(), e);
                                }
                            })
                            .filter(restAreaDescription ->
                                    restAreaDescription != RestAreaNullObject.INSTANCE)
                            .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error occurred when collecting data", e);
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }

        finalResponse.sort(
                Comparator.comparingDouble(
                        RestAreaDescription::getRating)
                        .reversed());

        return Response
                .ok(finalResponse)
                .build();
    }
}
