package com.epam.esm.validation;

import com.epam.esm.dto.ImageFileDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImageValidator implements Validator {
    private static final String IMAGE_PNG_CONTENT_TYPE = "image/png";
    private static final String FILE = "file";
    private static final String NAME = "name";
    private static final Long MAX_FILE_SIZE = 5368709120L;

    @Override
    public boolean supports(Class<?> clazz) {
        return ImageFileDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ImageFileDto imageFileDto = (ImageFileDto) target;
        if (!isValidNameValue(imageFileDto.getName())) {
            errors.rejectValue(NAME, "invalid.name");
        }
        if (!isValidFileSize(imageFileDto.getFile())) {
            errors.rejectValue(FILE, "invalid.size");
            return;
        }
        if (!isValidFileType(imageFileDto.getFile())) {
            errors.rejectValue(FILE, "invalid.type");
        }
    }

    private boolean isValidNameValue(String name) {
        if (name != null && !name.isEmpty()) {
            Pattern patter = Pattern.compile("^[\\w\\.\\(\\)\\-\\*]{1,1000}$");
            Matcher matcher = patter.matcher(name);
            return matcher.matches();
        }
        return false;
    }

    private boolean isValidFileType(MultipartFile file) {
        return IMAGE_PNG_CONTENT_TYPE.equalsIgnoreCase(file.getContentType());
    }

    private boolean isValidFileSize(MultipartFile file) {
        return file != null && !file.isEmpty() && file.getSize() < MAX_FILE_SIZE;
    }
}