package com.ieng.task.dests.advice;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocaleService {
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	public String getMessage(String code, HttpServletRequest request) {
		return messageSource.getMessage(code, null, localeResolver.resolveLocale(request));
	}

	public String getMessage(String code, HttpServletRequest request, Object[] args) {
		return messageSource.getMessage(code, args, localeResolver.resolveLocale(request));
	}

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, localeResolver.getDefaultLocale());
	}

	public String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, localeResolver.getDefaultLocale());
	}

	public String getMessage(String code, Locale locale) {
		return messageSource.getMessage(code, null, locale);
	}

	public String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

	public String getLocaleLanguage(HttpServletRequest request) {
		return localeResolver.resolveLocale(request).getLanguage() == Locale.ENGLISH.getLanguage() ? "en_US" : "ar_SA";
	}

	public Locale getLocale(HttpServletRequest request) {
		return localeResolver.resolveLocale(request);
	}

	public boolean isEnglish(HttpServletRequest request) {
		return localeResolver.resolveLocale(request).getLanguage() == Locale.ENGLISH.getLanguage() ? true : false;
	}

	public String getLanguage(HttpServletRequest request) {
		return localeResolver.resolveLocale(request).getLanguage();
	}
}
