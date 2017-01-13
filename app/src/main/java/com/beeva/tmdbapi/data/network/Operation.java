package com.beeva.tmdbapi.data.network;

import java.util.Map;

public class Operation {

    public enum Type {
        GET,
        POST
    }

    public final Type type;

    public final String url;

    public final Map<String, String> queryParams;

    public final String body;

    public static Operation newGet(String endpoint) {
        return newGet(endpoint, null);
    }

    public static Operation newGet(String endpoint, Map<String, String> queryParams) {
        return new Operation(Type.GET,
                endpoint,
                queryParams,
                null);
    }

    public static Operation newPost(String endpoint, String body) {
        return new Operation(Type.POST,
                endpoint,
                null,
                body);
    }

    private Operation(Type type, String endpoint, Map<String, String> queryParams, String body) {
        this.type = type;
        this.url = endpoint;
        this.queryParams = queryParams;
        this.body = body;
    }
}
