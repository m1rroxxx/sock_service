package com.backspark.sock_service.sort;

import java.util.Comparator;

public interface SortingParameter<T, U> {
    boolean isApplicable(U sortBy);

    Comparator<T> getComparator();
}
