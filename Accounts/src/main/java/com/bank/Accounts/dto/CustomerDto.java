package com.bank.Accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;


// to enhance documentation use implement
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Accounts information"
)
@Data
public class CustomerDto {

    @Schema(description = "Name of the customer",  example = "EazyBytes") // for documentation
    @Size( min = 4 , max = 40, message = "please probile a valid name")
    @NotEmpty(message = "name cannot be null or empty")
    private String name;

    @Schema(description = "Name of the mail",  example = "xyz@mail.com")
    @NotEmpty(message = "email address cannot be null")
    @Email(message = "Please enter a valid email")
    private String email;

    @Schema(description = "mobile numbers",  example = "XXXXXXXXXX")
    @NotEmpty(message = "MobileNumber cannot be null")
    @Pattern( regexp = "[0-9]{10}", message = "Mobile number must be 10 digit")
    private String mobileNumber;

    @Schema(description = "account details of a  customer")
    private AccountsDto accountsDto;
}
