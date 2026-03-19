package com.bank.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// to enhance documentation use implement
@Schema(
        name = "Accounts",
        description = "Schema should hold Account information"
)
@Data
public class AccountsDto {

    @Schema(description = "account number of EazyBank account")
    @Pattern( regexp = "^$[0-9]{10}", message = "Account number must be 10 digit")
    @NotEmpty(message = "Account number cannot be null or empty")
    private Long accountNumber;

    @Schema(description = " EazyBank customers account type", example = "Savings")
    @NotEmpty(message = "Account type cannot be null")
    private String accountType;

    @Schema(description = "branch address of customers")
    @NotEmpty(message = "Branch address cannot be null")
    private String branchAddress;

}
