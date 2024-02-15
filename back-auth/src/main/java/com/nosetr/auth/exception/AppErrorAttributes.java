package com.nosetr.auth.exception;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.google.common.base.CaseFormat;
import com.nosetr.library.exception.ApiException;
import com.nosetr.library.exception.AuthException;
import com.nosetr.library.exception.EntityAlreadyExistsException;
import com.nosetr.library.exception.EntityNotFoundException;
import com.nosetr.library.exception.UnauthorizedException;
import com.nosetr.library.exception.UnprocessableEntityException;
import com.nosetr.library.helper.MessageSourceHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * Put HttpStatus for each exception.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@Slf4j
@Component
public class AppErrorAttributes extends DefaultErrorAttributes {

	/**
	 * Constructor
	 * 
	 * @autor Nikolay Osetrov
	 * @since 0.1.0
	 */
	public AppErrorAttributes() {
		super();
	}

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest request, ErrorAttributeOptions options) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Throwable error = getError(request);

		ArrayList<Map<String, Object>> errorList = new ArrayList<>();

		// UNAUTHORIZED
		if (
			error instanceof AuthException || error instanceof UnauthorizedException
		) {
			log.error("UNAUTHORIZED_EXCEPTION: ", error);
			status = HttpStatus.UNAUTHORIZED;
			errorList.add(createErrorMap(((ApiException) error).getErrorCode(), error.getMessage()));
		} // UNAUTHORIZED JwtException
		else if (
			error instanceof ExpiredJwtException || error instanceof SignatureException
					|| error instanceof MalformedJwtException
		) {
			log.error("UNAUTHORIZED_JWT_EXCEPTION: ", error);
			status = HttpStatus.UNAUTHORIZED;
			errorList.add(createErrorMap("UNAUTHORIZED", error.getMessage()));
		}
		// NOT_FOUND
		else if (error instanceof NotFound || error instanceof NoResourceFoundException) {
			log.error("NOT_FOUND_EXCEPTION: ", error);
			status = HttpStatus.NOT_FOUND;
			errorList.add(createErrorMap("NOT_FOUND", error.getMessage()));
		}
		// NOT_FOUND
		else if (error instanceof EntityNotFoundException) {
			log.error("NOT_FOUND_EXCEPTION: ", error);
			status = HttpStatus.NOT_FOUND;
			errorList.add(createErrorMap(((ApiException) error).getErrorCode(), error.getMessage()));
		}
		// CONFLICT
		else if (error instanceof EntityAlreadyExistsException) {
			log.error("CONFLICT_EXCEPTION: ", error);
			status = HttpStatus.CONFLICT;
			errorList.add(createErrorMap(((ApiException) error).getErrorCode(), error.getMessage()));
		}
		// UNPROCESSABLE_ENTITY
		else if (error instanceof UnprocessableEntityException) {
			log.error("UNPROCESSABLE_ENTITY_EXCEPTION: ", error);
			status = HttpStatus.UNPROCESSABLE_ENTITY;
			errorList.add(createErrorMap("VALIDATION_FAILED", error.getMessage()));
		}
		// UNPROCESSABLE_ENTITY "VALIDATION_FAILED" of fields
		else if (error instanceof MethodArgumentNotValidException) {
			log.error("VALIDATION_FAILED_EXCEPTION: ", error);
			// Fields errors:
			Map<String, String> errors = new HashMap<>();
			BindingResult result = ((MethodArgumentNotValidException) error).getBindingResult();
			result.getFieldErrors()
					.forEach(
							err -> {
								// Guava convert Camel Case in Snake Case:
								String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, err.getField());
								if (errors.containsKey(field)) {
									errors.put(
											field, String.format("%s,%s", errors.get(field), err.getDefaultMessage())
									);
								} else {
									errors.put(field, err.getDefaultMessage());
								}
							}
					);

			status = HttpStatus.UNPROCESSABLE_ENTITY;
			Map<String, Object> errorMap = createErrorMap(
					"FIELD_VALIDATION_FAILED", MessageSourceHelper.getMessage("validation.default")
			);
			errorMap.put("fields", errors);
			errorList.add(errorMap);
		}
		// BAD_REQUEST
		else if (error instanceof ApiException) {
			log.error("BAD_REQUEST_EXCEPTION: ", error);
			status = HttpStatus.BAD_REQUEST;
			errorList.add(createErrorMap(((ApiException) error).getErrorCode(), error.getMessage()));
		}
		// other
		else {
			log.error("OTHER_EXCEPTION: {}", error.fillInStackTrace());
			String message = MessageSourceHelper.getMessage("exception.default");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			errorList.add(createErrorMap("INTERNAL_ERROR", message));
		}

		Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
		errorAttributes.put("status", status.value());
		errorAttributes.put("errors", errorList);

		return errorAttributes;
	}

	/**
	 * Helper to create errorMap
	 * 
	 * @param  <T>
	 * @autor          Nikolay Osetrov
	 * @since          0.1.4
	 * @param  code
	 * @param  message
	 * @return
	 */
	private <T> Map<String, Object> createErrorMap(String code, T message) {
		Map<String, Object> errorMap = new LinkedHashMap<>();
		errorMap.put("code", code);
		errorMap.put("message", message);
		return errorMap;
	}
}
