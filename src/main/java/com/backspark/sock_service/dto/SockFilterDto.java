package com.backspark.sock_service.dto;

import com.backspark.sock_service.validator.ValidHexColor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "фильтры для получения общего количества носков")
public class SockFilterDto {

    @ValidHexColor
    @Schema(description = "будут получены носки таким цветом", example = "#ffffff")
    private String colorHex;

    @Min(value = 0, message = "cotton percentage must be less then 0")
    @Max(value = 100, message = "cotton percentage must be more than 100")
    @Schema(description = "будут получены носки с большим процентом хлопка")
    private Integer lessThanCottonPercentage;

    @Min(value = 0, message = "cotton percentage must be less then 0")
    @Max(value = 100, message = "cotton percentage must be more than 100")
    @Schema(description = "будут получены носки с меньшим процентом хлопка")
    private Integer moreThanCottonPercentage;

    @Min(value = 0, message = "cotton percentage must be less then 0")
    @Max(value = 100, message = "cotton percentage must be more than 100")
    @Schema(description = "будут получены носки только с этим процентом хлопка")
    private Integer equalCottonPercentage;
}
