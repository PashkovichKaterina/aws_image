package com.epam.esm.controller;

import com.amazonaws.util.IOUtils;
import com.epam.esm.converter.ImageConverter;
import com.epam.esm.dto.ErrorResponse;
import com.epam.esm.dto.ImageDto;
import com.epam.esm.dto.ImageFileDto;
import com.epam.esm.service.ImageService;
import com.epam.esm.validation.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@Validated
public class ImageController {
    private static final String ERRORS_MESSAGES_FILE = "errors_message";
    private static final String EMPTY_NAME_VALUE = "Name shouldn't be empty";
    private static final String INVALID_PARAMETER_ERROR_CODE = "invalidParameter";

    private ImageService imageService;
    private ImageValidator imageValidator;
    private ImageConverter imageConverter;

    @Autowired
    public ImageController(ImageService imageService, ImageValidator imageValidator, ImageConverter imageConverter) {
        this.imageService = imageService;
        this.imageValidator = imageValidator;
        this.imageConverter = imageConverter;
    }

    @InitBinder("imageFileDto")
    protected void initUploadImageBinder(WebDataBinder binder) {
        binder.addValidators(imageValidator);
    }

    @GetMapping("/{name}")
    public ResponseEntity getImageByName(@NotEmpty(message = EMPTY_NAME_VALUE) @PathVariable String name) throws IOException {
        InputStream imageStream = imageService.getByName(name);
        HttpHeaders headers = getImageHeaders();
        return new ResponseEntity<>(IOUtils.toByteArray(imageStream), headers, HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity getRandomImage() throws IOException {
        InputStream imageStream = imageService.getRandom();
        HttpHeaders headers = getImageHeaders();
        return new ResponseEntity<>(IOUtils.toByteArray(imageStream), headers, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity uploadImage(@Validated ImageFileDto imageFileDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = formErrorMessage(bindingResult);
            ErrorResponse errorResponse = new ErrorResponse(INVALID_PARAMETER_ERROR_CODE, errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ImageDto resultImage = imageConverter.toImageDto(
                imageService.upload(imageFileDto.getName(), imageFileDto.getFile()));
        URI createdResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ImageController.class)
                        .getImageByName(resultImage.getName()))
                .toUri();
        return ResponseEntity.created(createdResourceAddress).body(resultImage);
    }

    private HttpHeaders getImageHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.IMAGE_PNG);
        return headers;
    }

    private List<String> formErrorMessage(BindingResult bindingResult) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(ERRORS_MESSAGES_FILE);
        List<String> errorsList = new ArrayList<>();
        for (ObjectError errorObject : bindingResult.getAllErrors()) {
            errorsList.add(messageSource.getMessage(errorObject, Locale.ENGLISH));
        }
        return errorsList;
    }
}