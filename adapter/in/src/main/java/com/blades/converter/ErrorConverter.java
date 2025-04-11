package com.blades.converter;

import com.blades.data.error.ErrorDto;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ErrorConverter {

    public Set<ErrorDto> toErrorDto(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
            .map(fieldError -> new ErrorDto(fieldError.getDefaultMessage(), fieldError.getField()))
            .collect(Collectors.toSet());
    }

}
