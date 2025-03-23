package com.blades.frontend.config;

import com.samskivert.mustache.Mustache;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;

@ControllerAdvice
@Configuration
@AllArgsConstructor
public class LocalControllerAdvice {

    private static final String PROPERTY_WITH_ARGS_PATTERN = "([^\s]+) ?'?([^']+)?'?";

    private final MessageSource propertySource;

    @ModelAttribute("i18n")
    public Mustache.Lambda i18n(Locale locale) {
        return (frag, out) -> {
            String body = frag.execute();

            Matcher matcher = Pattern.compile(PROPERTY_WITH_ARGS_PATTERN).matcher(body);
            while (matcher.find()) {
                String property = matcher.group(1);
                Object[] args = (matcher.group(2) == null)
                                ? new String[]{}
                                : matcher.group(2).split(";");

                String message;
                try {
                    message = String.format(propertySource.getMessage(property, args, locale),
                                                   args);
                } catch (NoSuchMessageException e) {
                    message = body;
                }

                out.write(message);
            }
        };
    }

}
