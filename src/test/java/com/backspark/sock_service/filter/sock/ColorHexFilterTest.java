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
public class ColorHexFilterTest {

    @InjectMocks
    private ColorHexFilter colorHexFilter;

    @Test
    public void testIsApplicable() {
        SockFilterDto filter = new SockFilterDto();
        filter.setColorHex("#ffffff");

        boolean result = colorHexFilter.isApplicable(filter);
        assertTrue(result);
    }

    @Test
    public void testApply() {
        SockFilterDto filter = new SockFilterDto();
        filter.setColorHex("#ffffff");

        Stream<Sock> stream = Stream.of(new Sock());

        colorHexFilter.apply(filter, stream);
    }
}
