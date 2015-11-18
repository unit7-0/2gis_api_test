package com.unit7.gis.api.client;

import com.unit7.gis.api.model.Branch;
import com.unit7.gis.api.model.RestAreaDescription;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Клиент АПИ 2gis.
 *
 * Created by breezzo on 17.11.15.
 */
public interface ApiClient {
    /**
     * Найти наиболее подходящий филиал
     * @param what какой филиал искать
     * @param where где искать
     */
    @Nonnull
    Optional<Branch> findBestBranch(String what, String where);

    /**
     * Дополнить филиал полным описанием
     * @param branch филиал с id и хэшем
     * @param restAreaKind тип филиала
     */
    @Nonnull
    RestAreaDescription completeBranch(
            @Nonnull Branch branch,
            @Nullable String restAreaKind);
}
