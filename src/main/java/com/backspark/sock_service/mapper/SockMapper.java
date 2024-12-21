package com.backspark.sock_service.mapper;

import com.backspark.sock_service.dto.SockCreateDto;
import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.entity.Sock;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SockMapper {

    SockDto toDto(Sock sock);
    List<SockDto> toDto(List<Sock> socks);

    Sock toSock(SockCreateDto sockRegisterDto);

    void updateSock(SockDto sockUpdateDto, @MappingTarget Sock sock);
}
