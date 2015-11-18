package com.unit7.gis.api.client;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.unit7.gis.api.model.Branch;
import com.unit7.gis.api.model.BranchProfileDescription;
import com.unit7.gis.api.model.BranchSearchResponse;
import com.unit7.gis.api.model.RestAreaDescription;
import com.unit7.gis.api.settings.ServiceSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.unit7.gis.api.client.UriQuery.QueryBuilder.ParameterName.*;

/**
 * Created by breezzo on 17.11.15.
 */
public class ApiClientImpl implements ApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ApiClientImpl.class);
    private static final Gson gson = new Gson();
    private static final ApiErrorHandler errorHandler = new ApiErrorHandler();

    @Nonnull
    @Override
    public Optional<Branch> findBestBranch(String what, String where) {
        logger.debug("find: {}, in: {}", what, where);

        Query query = new UriQuery.QueryBuilder()
                .search()
                .addParameter(KEY, ServiceSettings.get().apiKey)
                .addParameter(VERSION, ServiceSettings.get().apiVersion)
                .addParameter(WHAT, what)
                .addParameter(WHERE, where)
                .addParameter(SORT, "rating")
                .addParameter(PAGE_SIZE, "5")
                .addParameter(OUTPUT, "json")
                .build();

        final Optional<byte[]> response =
                HttpClientInst.instance().executeGetRequest(URI.create(query.toString()));

        if (!response.isPresent())
            throw new RuntimeException("Api service does not send any content");

        BranchSearchResponse branchSearchResponse = gson.fromJson(
                new InputStreamReader(new ByteArrayInputStream(response.get())),
                BranchSearchResponse.class);

        errorHandler.handleIfError(branchSearchResponse);

        List<Branch> result = branchSearchResponse.getResult();

        return result != null && !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Nonnull
    @Override
    public RestAreaDescription completeBranch(
            @Nonnull Branch branch,
            @Nullable String restAreaKind) {

        Query query = new UriQuery.QueryBuilder()
                .profile()
                .addParameter(KEY, ServiceSettings.get().apiKey)
                .addParameter(VERSION, ServiceSettings.get().apiVersion)
                .addParameter(ID, branch.getId())
                .addParameter(HASH, branch.getHash())
                .addParameter(OUTPUT, "json")
                .build();

        final Optional<byte[]> response =
                HttpClientInst.instance().executeGetRequest(URI.create(query.toString()));

        if (!response.isPresent())
            throw new RuntimeException("Api service does not send any content");

        BranchProfileDescription profile = gson.fromJson(
                new InputStreamReader(new ByteArrayInputStream(response.get())),
                BranchProfileDescription.class);

        errorHandler.handleIfError(profile);

        RestAreaDescription resultProfile = new RestAreaDescription();

        resultProfile.setName(profile.getName());
        resultProfile.setRating(profile.getRating());
        resultProfile.setAddress(
                Joiner.on(", ")
                        .skipNulls()
                        .join(profile.getCityName(), profile.getAddress()));

        return resultProfile;
    }
}
