package com.backspark.sock_service.filter.sock;

import com.backspark.sock_service.dto.SockFilterDto;
import com.backspark.sock_service.entity.Sock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LessThanCottonPercentageFilterTest {
    @InjectMocks
    private LessThanCottonPercentageFilter lessThanCottonPercentageFilter;

    @Test
    public void testIsApplicable() {
        SockFilterDto filter = new SockFilterDto();
        filter.setLessThanCottonPercentage(10);

        boolean result = lessThanCottonPercentageFilter.isApplicable(filter);
        assertTrue(result);
    }

    @Test
    public void testApply() {
        SockFilterDto filter = new SockFilterDto();
        filter.setLessThanCottonPercentage(10);

        Stream<Sock> stream = Stream.of(new Sock());

        lessThanCottonPercentageFilter.apply(filter, stream);
    }
}
