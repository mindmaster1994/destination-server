package com.ieng.task.dests.advice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocaleResolver extends AcceptHeaderLocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		
		return super.getDefaultLocale();
		
	}
}