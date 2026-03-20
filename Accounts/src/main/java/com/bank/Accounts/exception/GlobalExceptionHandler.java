package com.bank.Accounts.exception;

import com.bank.Accounts.dto.ErrorResponseDto;
//import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

@ControllerAdvice // whenever exception happens in any controller please invoke a methods that i am writing inside this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * handle the exception where the customer already exist
     * @param exception
     * @param webRequest
     * @return
     */
    @ExceptionHandler(CustomerAlreadyExistException.class) // map the exception to the handler method
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(CustomerAlreadyExistException exception , WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // map the exception to the handler method
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception , WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }


//    Method Argument Error input from postman/frontend
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
                                    (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String > validationsError = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getAllErrors();

        validationErrorList.forEach(( error) -> {
            String fieldName=((FieldError) error).getField();
            String validationMessage= error.getDefaultMessage();
            validationsError.put(fieldName, validationMessage);
        });

        return new ResponseEntity<>(validationsError, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerGlobalException(Exception exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
              webRequest.getDescription(false),
              HttpStatus.INTERNAL_SERVER_ERROR,
              exception.getMessage(),
              LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
