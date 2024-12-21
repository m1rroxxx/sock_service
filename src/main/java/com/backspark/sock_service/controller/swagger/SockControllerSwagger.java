package com.backspark.sock_service.controller.swagger;

import com.backspark.sock_service.dto.SockCreateDto;
import com.backspark.sock_service.dto.SockDto;
import com.backspark.sock_service.dto.SockFilterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Sock controller", description = "Operations related to socks")
public interface SockControllerSwagger {

    @Operation(summary = "Регистрация прихода носков")
    SockDto registerSocksArrival(SockCreateDto sockRegisterDto);

    @Operation(summary = "Регистрация отпуска носков")
    SockDto registerSockRelease(SockCreateDto sockRegisterDto);

    @Operation(summary = "Загрузка партий носков из CSV файла")
    List<SockDto> registerBatchSocks(@Parameter(description = "файл csv.csv") @RequestParam("csv") MultipartFile csv);

    @Operation(summary = "Получение общего количества носков с фильтрацией и сортировкой",
            description = "если фильтр не нужен, задать ему значение null или ек включать в объект")
    List<SockDto> getAllSocks(@Parameter(description = "объект фильтров") SockFilterDto filter,
                              @Parameter(description = "указать имя поля по которому нужно отсортировать") String sortBy,
                              @Parameter(description = "asc или desc") String order);

    @Operation(summary = "Получение носков по id")
    SockDto getSockById(long id);

    @Operation(summary = "Обновление данных носков")
    SockDto updateSock(SockDto sockUpdateDto);


}
