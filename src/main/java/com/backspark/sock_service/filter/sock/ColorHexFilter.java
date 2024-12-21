package com.backspark.sock_service.filter.sock;

import com.backspark.sock_service.dto.SockFilterDto;
import com.backspark.sock_service.entity.Sock;
import com.backspark.sock_service.filter.Filter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ColorHexFilter implements Filter<Sock, SockFilterDto> {
    @Override
    public boolean isApplicable(SockFilterDto filter) {
        return filter.getColorHex() != null;
    }

    @Override
    public Stream<Sock> apply(SockFilterDto filter, Stream<Sock> stream) {
        return stream.filter(sock -> sock.getColorHex().equals(filter.getColorHex()));
    }
}
