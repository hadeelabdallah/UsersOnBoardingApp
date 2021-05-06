package com.hadeel.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    ResourceBundleMessageSource resourceBundleMessageSource;

    @GetMapping("/hello-world-int")
    public String getHelloWorld(){
        return resourceBundleMessageSource.getMessage("message.good.morning", null, LocaleContextHolder.getLocale());
    }
}
