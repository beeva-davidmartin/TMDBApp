package com.beeva.tmdbapi.domain;

public class TMDbException extends Exception {

    public final Type type;

    public enum Type {
        GENERAL,
        NETWORK,
        API,
        INTERNAL
    }

    public TMDbException(Type type) {
        super();
        this.type = type;
    }

    public TMDbException(Type type, Throwable cause) {
        super(cause);
        this.type = type;
    }
}
