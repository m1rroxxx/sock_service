package com.backspark.sock_service.dto;

import com.backspark.sock_service.validator.ValidHexColor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "сущность носков")
public class SockDto {

    @NotNull(message = "id must not be null")
    private Long id;

    @ValidHexColor
    @NotNull(message = "color hex must not be null")
    @Schema(description = "hex", example = "#ffffff")
    private String colorHex;

    @NotNull(message = "cotton percentage must not be null")
    @Min(value = 0, message = "cotton percentage must be less then 0")
    @Max(value = 100, message = "cotton percentage must be more than 100")
    private Integer cottonPercentage;

    @NotNull(message = "quantity must not be null")
    @Min(value = 0, message = "quantity must be non-negative")
    private Integer quantity;
}
