package com.unit7.gis.api.client;

/**
 * Created by breezzo on 17.11.15.
 */
public class UriQuery implements Query {

    private String uriString;

    public UriQuery(String uriString) {
        this.uriString = uriString;
    }

    @Override
    public String toString() {
        return uriString;
    }

    public static class QueryBuilder {
        private static final String API_PART = "http://catalog.api.2gis.ru/";
        private static final String SEARCH_QUERY_PART = "search";
        private static final String PROFILE_QUERY_PART = "profile";

        private String baseUri = API_PART;
        private String servicePart;

        private StringBuilder parametersPart = new StringBuilder("?");

        public QueryBuilder search() {
            servicePart = SEARCH_QUERY_PART;
            return this;
        }

        public QueryBuilder profile() {
            servicePart = PROFILE_QUERY_PART;
            return this;
        }

        public QueryBuilder baseUri(String uri) {
            this.baseUri = uri;
            return this;
        }

        public QueryBuilder addParameter(ParameterName name, String value) {
            if (parametersPart.length() > 1)
                parametersPart.append("&");

            parametersPart
                    .append(name.getKey())
                    .append("=")
                    .append(value);
            return this;
        }

        public Query build() {
            String uri = new StringBuilder(baseUri)
                    .append(servicePart)
                    .append(parametersPart.toString())
                    .toString();

            return new UriQuery(uri);
        }

        enum ParameterName {
            KEY("key"),
            VERSION("version"),
            WHAT("what"),
            WHERE("where"),
            SORT("sort"),
            PAGE_SIZE("pagesize"),
            OUTPUT("output"),
            ID("id"),
            HASH("hash");

            ParameterName(String key) {
                this.key = key;
            }

            private final String key;

            public String getKey() {
                return key;
            }
        }
    }
}
