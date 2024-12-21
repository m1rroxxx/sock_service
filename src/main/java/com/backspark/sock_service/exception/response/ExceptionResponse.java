package com.backspark.sock_service.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "объект возвращается если ошибка 400 - 499")
public class ExceptionResponse {
    private int statusCode;
    private HttpStatus status;
    private List<String> errorMessages;
}
