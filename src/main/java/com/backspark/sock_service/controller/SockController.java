package com.backspark.sock_service.controller;

import com.backspark.sock_service.controller.swagger.SockControllerSwagger;
import com.backspark.sock_service.dto.SockCreateDto;
import com.backspark.sock_service.dto.SockFilterDto;
import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.service.SockService;
import com.backspark.sock_service.sort.SortingParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/socks")
public class SockController implements SockControllerSwagger {
    private final SockService sockService;
    private final List<SortingParameter<SockDto, String>> sortingParameters;

    @PostMapping("/income")
    public SockDto registerSocksArrival(@RequestBody @Validated SockCreateDto sockRegisterDto) {
        return sockService.registerSockArrival(sockRegisterDto);
    }

    @PostMapping("/outcome")
    public SockDto registerSockRelease(@RequestBody @Validated SockCreateDto sockRegisterDto) {
        return sockService.registerSockRelease(sockRegisterDto);
    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<SockDto> registerBatchSocks(@RequestBody @Validated MultipartFile csv) {
        return sockService.loadingBatchesFromCsv(csv);
    }

    @PostMapping
    public List<SockDto> getAllSocks(@RequestBody @Validated SockFilterDto filter,
                                     @RequestParam(required = false) String sortBy,
                                     @RequestParam(required = false) String order) {

        List<SockDto> response = sockService.getAllSocks(filter);

        if (sortBy != null) {
            return response.stream().sorted(getComparator(sortBy, order)).toList();
        }
        return response;
    }

    @GetMapping("{id}")
    public SockDto getSockById(@PathVariable long id) {
        return sockService.getSockDtoById(id);
    }

    @PutMapping
    public SockDto updateSock(@RequestBody @Validated SockDto sockUpdateDto) {
        return sockService.updateSock(sockUpdateDto);
    }

    private Comparator<SockDto> getComparator(String sortBy, String order) {

        Comparator<SockDto> comparator = sortingParameters.stream()
                .filter(param -> param.isApplicable(sortBy))
                .map(SortingParameter::getComparator)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unsupported sortBy: " + sortBy));

        if (order != null && order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
