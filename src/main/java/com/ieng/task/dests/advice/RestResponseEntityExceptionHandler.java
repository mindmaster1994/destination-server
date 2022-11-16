package com.ieng.task.dests.advice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ieng.task.dests.exception.BusinessException;
import com.ieng.task.dests.exception.GeneralException;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private LocaleService localeService;

	@Autowired
	public RestResponseEntityExceptionHandler(LocaleService localeService) {
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {

		log.error(ex.getMessage(), ex);
		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.error(new ErrorBody(HttpStatus.FORBIDDEN, ex.getMessage())).build();
		return new ResponseEntity<Object>(envelop, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {

		log.error(ex.getMessage(), ex);
		String error = localeService.getMessage("general.error", request);
		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.error(new ErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, error)).build();
		return new ResponseEntity<Object>(envelop, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<Object> handleBadCredentialsException(Exception ex, HttpServletRequest request) {

		log.error(ex.getMessage(), ex);
		String error = localeService.getMessage("app.bad.credentials", request);
		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.error(new ErrorBody(HttpStatus.UNAUTHORIZED, error)).build();
		return new ResponseEntity<Object>(envelop, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ GeneralException.class })
	public ResponseEntity<Object> handleGeneralException(GeneralException ex, HttpServletRequest request) {
		return handleException(ex, request);
	}

	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletRequest request) {

		String error = localeService.getMessage(ex.getLocalizedMsgKey().trim(), request, ex.getLocalizedMsgArgs());
		log.error("Business Exception:" + error + "::" + ex.getErrorCode(), ex.getErrorCode());
		String errorCode = ex.getErrorCode();
		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.error(new ErrorBody(errorCode, error)).build();

		return new ResponseEntity<Object>(envelop, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(ex.getMessage(), ex);
		String error = localeService.getMessage("unprocessable.entity.error", request.getLocale());
		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.error(new ErrorBody(HttpStatus.UNPROCESSABLE_ENTITY, error)).build();

		return new ResponseEntity<Object>(envelop, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error(ex.getMessage(), ex);
		List<ErrorBody> errorBodyList = ex.getBindingResult().getAllErrors().stream().map(
				item -> new ErrorBody(status, localeService.getMessage(item.getDefaultMessage(), request.getLocale())))
				.collect(Collectors.toList());
		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.errors(errorBodyList).build();

		return new ResponseEntity<Object>(envelop, HttpStatus.OK);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {

		log.error(ex.getMessage(), ex);

		ResponseEnvelope envelop = ResponseEnvelope.builder().success(false).time(LocalDateTime.now())
				.errors(Arrays.asList(new ErrorBody(HttpStatus.BAD_REQUEST,
						localeService.getMessage(ex.getConstraintName(), request.getLocale()))))
				.build();

		return new ResponseEntity<Object>(envelop, HttpStatus.OK);
	}

}
