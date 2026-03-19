package com.bank.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Response",description = "This Schema to hold Response information")
public class ResponseDto {

    @Schema(description = "Status code", example = "200")
    private String statusCode;

    @Schema(description = "Status message in the response ", example = "")
    private String statusMsg;


}
