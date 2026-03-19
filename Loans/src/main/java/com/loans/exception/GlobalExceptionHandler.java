package com.loans.exception;

import com.loans.dto.ErrorResponseDto;
import com.loans.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


//   apiPath, errorCode, errorMessage, errorTime we returning a ErrorResponseDto to send appropriate fields to user

    /**
     * to handle handleMethodArgumentNotValid
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        took a HashMap ans by the MethodArgumentNotValidException object
//                fetched all the ObjectEror
        Map<String, String> validationError = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(
                (error) -> {
                   String fieldName= ((FieldError)error).getField();
                   String validationMessage = error.getDefaultMessage();
                   validationError.put(fieldName, validationMessage);
                }
        );
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    /**
     * to handle all type of Exception
     * @param exception
     * @param webRequest
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * to handle LoanAlreadyExistsException
     * @param exception
     * @param webRequest
     * @return ErrorResponseDto
     */
    @ExceptionHandler(LoanAlreadyExistsException.class)
   public ResponseEntity<ErrorResponseDto> handleLoanAlreadyExistsException(LoanAlreadyExistsException exception , WebRequest webRequest){
        ErrorResponseDto ErrorResponseDto =new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(ErrorResponseDto , HttpStatus.BAD_REQUEST);
    }

    /**
     * to handle all the ResourceNotFoundException
     * @param exception
     * @param webRequest
     * @return ErrorResponseDto
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleLoanResourceNotFoundException(ResourceNotFoundException exception , WebRequest webRequest){
        ErrorResponseDto ErrorResponseDto =new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(ErrorResponseDto , HttpStatus.NOT_FOUND);
    }
}
