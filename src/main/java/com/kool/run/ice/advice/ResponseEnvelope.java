package com.ieng.task.dests.advice;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResponseEnvelope {

	private boolean success;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Object result;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Object data;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	LocalDateTime time;

	@Singular
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<ErrorBody> errors;
}
