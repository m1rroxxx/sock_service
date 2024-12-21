package com.backspark.sock_service.service;

import com.backspark.sock_service.dto.SockCreateDto;
import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.dto.SockFilterDto;
import com.backspark.sock_service.entity.Sock;
import com.backspark.sock_service.filter.Filter;
import com.backspark.sock_service.mapper.SockMapperImpl;
import com.backspark.sock_service.repository.SockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SockServiceTest {

    @InjectMocks
    private SockService sockService;

    @Mock
    private SockRepository sockRepository;

    @Spy
    private SockMapperImpl sockMapper;

    @Mock
    private Validator validator;

    @Mock
    private Filter<Sock, SockFilterDto> filter;

    @BeforeEach
    public void setUp() {
        sockService = new SockService(
                sockRepository,
                sockMapper,
                List.of(filter, filter),
                validator
        );
    }

    @Test
    public void testRegisterSockArrival() {
        Sock sock = new Sock();
        sock.setQuantity(10);

        SockCreateDto sockCreateDto = new SockCreateDto();
        sockCreateDto.setColorHex("#ffffff");
        sockCreateDto.setCottonPercentage(20);
        sockCreateDto.setQuantity(10);

        when(sockRepository.findByColorHexAndCottonPercentage(
                sockCreateDto.getColorHex(),
                sockCreateDto.getCottonPercentage())
        ).thenReturn(Optional.of(sock));
        when(sockRepository.save(any())).thenReturn(sock);

        sockService.registerSockArrival(sockCreateDto);
    }

    @Test
    public void testRegisterSockRelease() {
        Sock sock = new Sock();
        sock.setQuantity(20);

        SockCreateDto sockCreateDto = new SockCreateDto();
        sockCreateDto.setColorHex("#ffffff");
        sockCreateDto.setCottonPercentage(20);
        sockCreateDto.setQuantity(10);

        when(sockRepository.findByColorHexAndCottonPercentage(
                sockCreateDto.getColorHex(),
                sockCreateDto.getCottonPercentage())
        ).thenReturn(Optional.of(sock));
        when(sockRepository.save(any())).thenReturn(sock);

        sockService.registerSockArrival(sockCreateDto);
    }

    @Test
    public void testGetAllSocks() {
        List<Sock> socks = List.of(new Sock(), new Sock());

        SockFilterDto filterDto = new SockFilterDto();

        when(sockRepository.findAll()).thenReturn(socks);
        when(filter.isApplicable(filterDto)).thenReturn(false);

        sockService.getAllSocks(filterDto);
    }

    @Test
    public void testGetSockDtoById() {
        long id = 1L;

        SockDto sockDto = new SockDto();
        sockDto.setId(id);

        when(sockRepository.findById(id)).thenReturn(Optional.of(new Sock()));

        sockService.getSockDtoById(sockDto.getId());
    }

    @Test
    public void testUpdateSock() {
        SockDto sockDto = new SockDto();
        sockDto.setId(1L);

        Sock sock = new Sock();

        when(sockRepository.findById(sockDto.getId())).thenReturn(Optional.of(sock));
        when(sockRepository.save(sock)).thenReturn(sock);

        sockService.updateSock(sockDto);
    }

}
