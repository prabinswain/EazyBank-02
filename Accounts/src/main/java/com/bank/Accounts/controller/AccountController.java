package com.bank.Accounts.controller;


import com.bank.Accounts.constants.AccountsConstans;
import com.bank.Accounts.dto.CustomerDto;
import com.bank.Accounts.dto.ErrorRespinseDto;
import com.bank.Accounts.dto.ResponseDto;
import com.bank.Accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


// Controller layer is responsible for to receive the request from the postman or frontend
@RestController  // @Controller + @ResponseBody = for returning JSON and XML data
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
// to implement openAPI specification
@Tag(
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs for Accounts in EazyBank to CREATE, UPDATE, FETCH AND DELETE Details"
)
public class AccountController {

    private IAccountService accountService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status CREATED"
    )
   // create an account on customer details
//    @Valid is for to validate the input URL body
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstans.STATUS_201, AccountsConstans.MESSAGE_201
        ));
    }

    @Operation(
            summary = "Fetch account detailsr",
            description = "Fetch account details by mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    // fetch account details by mobileNumber
    @GetMapping(path = "/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails( @RequestParam
                                                                @Pattern(regexp = "[0-9]{10}", message = "Account number must be 10 digit")
                                                                String mobileNumber){
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update account details",
            description = "update account & customer details"
    )
    @ApiResponses({ @ApiResponse (
            responseCode = "200",
            description = "Http Status OK"
    ),
            @ApiResponse (
                    responseCode = "500",
                    description = "Http Status INTERNAL_SERVER_ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorRespinseDto.class)
                    )
            )}

    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody @Valid CustomerDto customerDto){

        boolean updated = accountService.updateAccount(customerDto);
        if (updated)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstans.STATUS_200, AccountsConstans.MESSAGE_200));
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstans.STATUS_500,AccountsConstans.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete Account Details",
            description = "REST API for Delete account details "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status INTERNAL_SERVER_ERROR"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                              @Pattern(regexp = "[0-9]{10}", message = "Account number must be 10 digit")
                                                                String mobileNumber){

        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstans.STATUS_200, AccountsConstans.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstans.STATUS_500, AccountsConstans.MESSAGE_500));
        }
    }
}
