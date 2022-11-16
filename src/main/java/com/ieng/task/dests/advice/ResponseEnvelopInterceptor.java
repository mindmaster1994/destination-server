package com.ieng.task.dests.advice;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseEnvelopInterceptor implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

		if (returnType.getContainingClass().getCanonicalName().startsWith("springfox.documentation")) {
			return false;
		}
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		if (skipEnvelopingTheResponse(body, returnType)) {
			return body;
		}

		return ResponseEnvelope.builder().success(true).result(body).build();
	}

	private boolean skipEnvelopingTheResponse(Object body, MethodParameter returnType) {
		if (body instanceof ResponseEnvelope) {
			return true;
		}
		if (body instanceof String) {
			return true;
		}
		if (body instanceof InputStreamResource) {
			return true;
		}
		if (isActuatorRequest(returnType)) {
			return true;
		}

		return false;
	}

	private boolean isActuatorRequest(MethodParameter returnType) {
		return returnType != null && returnType.getDeclaringClass().getPackage().getName()
				.contains("org.springframework.boot.actuate.endpoint");
	}

}