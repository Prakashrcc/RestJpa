package com.pks.demo.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class RestErrorModel {
	private final LocalDateTime localDateTime;
	private final HttpStatus httpStatus;
	private final String message;

	public RestErrorModel(String message, HttpStatus httpStatus, LocalDateTime localDateTime) {
		super();
		this.message = message;

		this.httpStatus = httpStatus;
		this.localDateTime = localDateTime;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

}
