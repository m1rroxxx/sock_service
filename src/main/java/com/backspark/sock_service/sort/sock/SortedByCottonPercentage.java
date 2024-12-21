package com.backspark.sock_service.sort.sock;

import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.sort.SortingParameter;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class SortedByCottonPercentage implements SortingParameter<SockDto, String> {
    @Override
    public boolean isApplicable(String sortBy) {
        return sortBy.equalsIgnoreCase("Cotton-percentage") || sortBy.equalsIgnoreCase("CottonPercentage");
    }

    @Override
    public Comparator<SockDto> getComparator() {
        return Comparator.comparing(SockDto::getCottonPercentage);
    }
}
