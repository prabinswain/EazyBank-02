package com.bank.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name="Error response ",description = "Schema hold Error response information")
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API path invocked by client")
    private String apiPath;

    @Schema(description = "Errorcode represent the error happened")
    private HttpStatus errorCode;

    @Schema(description = "error message ")
    private String errorMsg;

    @Schema(description = "this hold error occured time")
    private LocalDateTime errorTime;
}
