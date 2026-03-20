package com.bank.cards.controller;

import com.bank.cards.constants.CardsConstants;
import com.bank.cards.dto.CardsContactInfoDto;
import com.bank.cards.dto.CardsDto;
import com.bank.cards.dto.ErrorResponseDto;
import com.bank.cards.dto.ResponseDto;
import com.bank.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Cards in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)

@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
//@AllArgsConstructor
@Validated
public class CardsController {

    private final ICardsService iCardsService;
    private final CardsContactInfoDto contactInfoDto;
    @Autowired
    private Environment environment;

    @Value("${build.version}")
    private String buildVersion;

    public CardsController(ICardsService iCardsService, CardsContactInfoDto contactInfoDto) {
        this.iCardsService = iCardsService;
        this.contactInfoDto = contactInfoDto;
    }

    @Operation(summary = "Create Card REST API",
            description = "REST API to create new Card inside EazyBank")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "HTTP Status CREATED"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        iCardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201 , CardsConstants.MESSAGE_201));
    }
    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(path = "/fetch")
    public  ResponseEntity<CardsDto> fetchCardDetails(@Valid @RequestParam
                                                          @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get build informatation",
            description = "Get build information that is deployed into cards microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),

    })
    // to fetch build version and return
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }
    @Operation(summary = "Get Java Information", description = "Get Java information details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Http Status code OK"),
            @ApiResponse(responseCode = "500",description = "Http Status code Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/java-info")
    public ResponseEntity<String> getJavaInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(summary = "Get Contact Information", description = "Get contact information details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Http Status code OK"),
            @ApiResponse(responseCode = "500",description = "Http Status code Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactInfoDto);
    }
}
