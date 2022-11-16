package com.ieng.task.dests.response;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Message {

	private static MessageSource messageSource;

	@Autowired
	public Message(MessageSource messageSource) {
		Message.messageSource = messageSource;
	}

	public static String value(String code) {
		return Message.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}

	public static String value(String code, Object... params) {
		return Message.messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
	}

	public static String value(String code, Locale locale) {
		return Message.messageSource.getMessage(code, null, locale);
	}

}
