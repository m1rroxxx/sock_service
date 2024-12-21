package com.backspark.sock_service.filter;

import java.util.stream.Stream;

public interface Filter<T, U> {
    boolean isApplicable(U filter);
    Stream<T> apply(U filter, Stream<T> stream);
}
