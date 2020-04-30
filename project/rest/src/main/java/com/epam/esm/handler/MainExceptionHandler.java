package com.epam.esm.handler;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.epam.esm.dto.ErrorResponse;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String EXIST_ENTITY_ERROR_CODE = "entityIsAlreadyExist";
    private static final String NO_SUCH_ENTITY_ERROR_CODE = "noSuchEntity";
    private static final String AMAZON_WEB_SERVICE_ERROR_CODE = "amazonWebServiceError";
    private static final String AMAZON_WEB_SERVICE_MESSAGE = "Check amazon S3 properties for your account";
    private static final String SDK_CLIENT_ERROR_CODE = "amazonS3ClientError";
    private static final String SDK_CLIENT_MESSAGE = "Check your amazon account data";
    private static final String INVALID_PARAMETER_ERROR_CODE = "invalidParameter";
    private static final String MAX_UPLOAD_FILE_SIZE_ERROR_CODE = "maxUploadFileSize";
    private static final String MAX_UPLOAD_FILE_SIZE_MESSAGE = "Maximum file size is 5GB";

    @ExceptionHandler(value = {SdkClientException.class})
    public ResponseEntity handleSdkClientException(SdkClientException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(SDK_CLIENT_ERROR_CODE, SDK_CLIENT_MESSAGE);
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {AmazonServiceException.class})
    public ResponseEntity handleAmazonServiceException(AmazonServiceException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(AMAZON_WEB_SERVICE_ERROR_CODE, AMAZON_WEB_SERVICE_MESSAGE);
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {EntityIsAlreadyExistsException.class})
    public ResponseEntity handleEntityIsAlreadyExistsException(EntityIsAlreadyExistsException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(EXIST_ENTITY_ERROR_CODE, e.getMessage());
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NoSuchEntityException.class})
    public ResponseEntity handleNoSuchEntityException(NoSuchEntityException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(NO_SUCH_ENTITY_ERROR_CODE, e.getMessage());
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public ResponseEntity handleNoSuchEntityException(MaxUploadSizeExceededException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(MAX_UPLOAD_FILE_SIZE_ERROR_CODE, MAX_UPLOAD_FILE_SIZE_MESSAGE);
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation c : e.getConstraintViolations()) {
            errors.add(c.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(INVALID_PARAMETER_ERROR_CODE, errors);
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}