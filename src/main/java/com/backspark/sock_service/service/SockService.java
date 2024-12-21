package com.backspark.sock_service.service;

import com.backspark.sock_service.dto.SockCreateDto;
import com.backspark.sock_service.dto.SockFilterDto;
import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.entity.Sock;
import com.backspark.sock_service.filter.Filter;
import com.backspark.sock_service.mapper.SockMapper;
import com.backspark.sock_service.repository.SockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SockService {
    private final SockRepository sockRepository;
    private final SockMapper sockMapper;
    private final List<Filter<Sock, SockFilterDto>> filters;
    private final Validator validator;

    public SockDto registerSockArrival(SockCreateDto sockDto) {
        log.info("Register sock arrival {}", sockDto);

        Optional<Sock> optionalSock = sockRepository
                .findByColorHexAndCottonPercentage(sockDto.getColorHex(), sockDto.getCottonPercentage());

        if (optionalSock.isPresent()) {
            Sock sock = optionalSock.get();
            sock.setQuantity(sock.getQuantity() + sockDto.getQuantity());
            return sockMapper.toDto(sockRepository.save(sock));
        }
        return sockMapper.toDto(sockRepository.save(sockMapper.toSock(sockDto)));
    }

    public SockDto registerSockRelease(SockCreateDto sockDto) {
        log.info("Register sock release {}", sockDto);

        Sock sock = getSockByColorHexAndCottonPercentage(sockDto.getColorHex(), sockDto.getCottonPercentage());

        if (sock.getQuantity() < sockDto.getQuantity()) {
            throw new IllegalArgumentException("Quantity of socks is less");
        }

        sock.setQuantity(sock.getQuantity() - sockDto.getQuantity());
        return sockMapper.toDto(sockRepository.save(sock));
    }

    public List<SockDto> getAllSocks(SockFilterDto sockFilterDto) {
        log.info("Get all socks with filter {}", sockFilterDto);

        Stream<Sock> sockStream = sockRepository.findAll().stream();

        return filters.stream()
                .filter(filter -> filter.isApplicable(sockFilterDto))
                .reduce(sockStream,
                        (stream, filter) -> filter.apply(sockFilterDto, stream),
                        (s1, s2) -> s1)
                .map(sockMapper::toDto)
                .toList();
    }

    public SockDto getSockDtoById(long id) {
        return sockMapper.toDto(getSockById(id));
    }

    @Transactional
    public SockDto updateSock(SockDto sockUpdateDto) {
        log.info("Update sock {}", sockUpdateDto);

        Sock sock = getSockById(sockUpdateDto.getId());
        sockMapper.updateSock(sockUpdateDto, sock);

        return sockMapper.toDto(sockRepository.save(sock));
    }

    @Transactional
    public List<SockDto> loadingBatchesFromCsv(MultipartFile csv) {
        log.info("Loading batches from csv file");

        List<SockDto> batch = new ArrayList<>();

        try (Reader reader = new InputStreamReader(csv.getInputStream())) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(reader);

            for (CSVRecord record : records) {

                SockCreateDto sockDto = new SockCreateDto();
                sockDto.setColorHex(record.get("colorHex"));
                sockDto.setCottonPercentage(Integer.parseInt(record.get("cottonPart")));
                sockDto.setQuantity(Integer.parseInt(record.get("quantity")));

                validateSock(sockDto);
                batch.add(registerSockArrival(sockDto));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return batch;
    }

    private void validateSock(SockCreateDto sockDto) {
        log.info("Validate sock {}", sockDto);

        DataBinder dataBinder = new DataBinder(sockDto);
        dataBinder.setValidator(validator);
        dataBinder.validate();

        BindingResult bindingResult = dataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Sock getSockById(long id) {
        log.info("Get sock by id {}", id);
        return sockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Sock with id %s not found", id)));
    }

    private Sock getSockByColorHexAndCottonPercentage(String colorHex, int cottonPercentage) {
        log.info("getting sock with colorHex {} and cottonPercentage {}", colorHex, cottonPercentage);
        return sockRepository.findByColorHexAndCottonPercentage(colorHex, cottonPercentage)
                .orElseThrow(() -> new RuntimeException("Sock not found"));
    }
}
