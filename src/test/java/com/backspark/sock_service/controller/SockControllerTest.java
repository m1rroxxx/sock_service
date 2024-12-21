package com.backspark.sock_service.controller;

import com.backspark.sock_service.dto.SockCreateDto;
import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.dto.SockFilterDto;
import com.backspark.sock_service.service.SockService;
import com.backspark.sock_service.sort.SortingParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SockControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private SockController sockController;

    @Mock
    private SockService sockService;

    @Mock
    private SortingParameter<SockDto, String> sortingParameter;

    private List<SortingParameter<SockDto, String>> sortingParameters;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sockController).build();
        sortingParameters = List.of(sortingParameter, sortingParameter);
        sockController = new SockController(sockService, sortingParameters);
    }

    @Test
    public void testRegisterSocksArrival() throws Exception {
        SockCreateDto sockRegisterDto = new SockCreateDto();
        sockRegisterDto.setColorHex("#ffffff");
        sockRegisterDto.setCottonPercentage(50);
        sockRegisterDto.setQuantity(10);

        SockDto sockDto = new SockDto();

        sockDto.setColorHex("#ffffff");
        sockDto.setCottonPercentage(50);
        sockDto.setQuantity(10);

        when(sockService.registerSockArrival(any())).thenReturn(sockDto);

        mockMvc.perform(post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sockRegisterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colorHex").value("#ffffff"))
                .andExpect(jsonPath("$.cottonPercentage").value(50))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testRegisterSockRelease() throws Exception {
        SockCreateDto sockRegisterDto = new SockCreateDto();
        sockRegisterDto.setColorHex("#ffffff");
        sockRegisterDto.setCottonPercentage(50);
        sockRegisterDto.setQuantity(10);

        SockDto sockDto = new SockDto();

        sockDto.setColorHex("#ffffff");
        sockDto.setCottonPercentage(50);
        sockDto.setQuantity(10);

        when(sockService.registerSockRelease(any())).thenReturn(sockDto);

        mockMvc.perform(post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sockRegisterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colorHex").value("#ffffff"))
                .andExpect(jsonPath("$.cottonPercentage").value(50))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testRegisterBatchSocks() throws Exception {
        SockDto sockDto = new SockDto();

        sockDto.setColorHex("#ffffff");
        sockDto.setCottonPercentage(50);
        sockDto.setQuantity(10);

        MultipartFile csv = mock();

        when(sockService.loadingBatchesFromCsv(any())).thenReturn(List.of(sockDto, sockDto));

        mockMvc.perform(post("/api/socks/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(csv.getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetAllSocks() throws Exception {
        SockFilterDto filter = new SockFilterDto();
        SockDto sockDto = new SockDto();

        sockDto.setColorHex("#ffffff");
        sockDto.setCottonPercentage(50);
        sockDto.setQuantity(10);

        when(sockService.getAllSocks(filter)).thenReturn(List.of(sockDto, sockDto));

        mockMvc.perform(post("/api/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetSockById() throws Exception {
        SockDto sockDto = new SockDto();
        sockDto.setId(1L);
        sockDto.setColorHex("#ffffff");
        sockDto.setCottonPercentage(50);
        sockDto.setQuantity(10);

        when(sockService.getSockDtoById(sockDto.getId())).thenReturn(sockDto);

        mockMvc.perform(get("/api/socks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.colorHex").value("#ffffff"))
                .andExpect(jsonPath("$.cottonPercentage").value(50))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testUpdateSock() throws Exception {
        SockDto sockDto = new SockDto();
        sockDto.setId(1L);
        sockDto.setColorHex("#ffffff");
        sockDto.setCottonPercentage(50);
        sockDto.setQuantity(10);

        when(sockService.updateSock(sockDto)).thenReturn(sockDto);

        mockMvc.perform(put("/api/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sockDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.colorHex").value("#ffffff"))
                .andExpect(jsonPath("$.cottonPercentage").value(50))
                .andExpect(jsonPath("$.quantity").value(10));
    }
}
