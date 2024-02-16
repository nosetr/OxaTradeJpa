package com.nosetr.auth.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nosetr.library.exception.UnprocessableEntityException;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO to delete. It's only example
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Slf4j
@Deprecated
//@ControllerAdvice
public class GlobalExceptionHandler {

	// Default status
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	//	@ExceptionHandler({AuthException.class, UnauthorizedException.class})
	//	public ResponseEntity<Object> handleUnauthorizedException() {
	//		log.error("UNAUTHORIZED: ", ex);
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNAUTHORIZED;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}
	//
	//	@ExceptionHandler({ExpiredJwtException.class, SignatureException.class, MalformedJwtException.class})
	//	public ResponseEntity<Object> handleUnauthorizedJwtException() {
	//		log.error("UNPROCESSABLE_ENTITY: ", ex);
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNPROCESSABLE_ENTITY;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}
	//
	//	@ExceptionHandler(EntityNotFoundException.class)
	//	public ResponseEntity<Object> handleEntityNotFoundException() {
	//		log.error("UNPROCESSABLE_ENTITY: ", ex);
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNPROCESSABLE_ENTITY;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}
	//
	//	@ExceptionHandler(EntityAlreadyExistsException.class)
	//	public ResponseEntity<Object> handleEntityAlreadyExistsException() {
	//		log.error("UNPROCESSABLE_ENTITY: ", ex);
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNPROCESSABLE_ENTITY;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}	

	/**
	 * Handle after validation.
	 * 
	 * @autor     Nikolay Osetrov
	 * @since     0.1.4
	 * @param  ex
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class, UnprocessableEntityException.class })
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
		log.error("UNPROCESSABLE_ENTITY: ", ex);
		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
		status = HttpStatus.UNPROCESSABLE_ENTITY;
		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));

		return createResponse(errorList);
	}

	//	@ExceptionHandler(WebExchangeBindException.class)
	//	public ResponseEntity<Object> handleWebExchangeBindException() {
	//		log.error("UNPROCESSABLE_ENTITY: ", ex);
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNPROCESSABLE_ENTITY;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}
	//
	//	@ExceptionHandler(ApiException.class)
	//	public ResponseEntity<Object> handleApiException() {
	//		log.error("UNPROCESSABLE_ENTITY: ", ex);
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNPROCESSABLE_ENTITY;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}
	//
	//	@ExceptionHandler
	//	public ResponseEntity<Object> handleAllExceptions(Exception ex) {
	//		// Behandeln Sie alle anderen Ausnahmen hier und geben Sie eine entsprechende Antwort zurück
	//		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
	//		status = HttpStatus.UNPROCESSABLE_ENTITY;
	//		errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
	//
	//		return createResponse(errorList);
	//	}

	/**
	 * Helper to create an ResponseEntity
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  errorList
	 * @return
	 */
	private ResponseEntity<Object> createResponse(ArrayList<Map<String, Object>> errorList) {
		HashMap<String, Object> errors = new HashMap<>();
		errors.put("errors", errorList);
		return new ResponseEntity<>(errors, status);
	}

	/**
	 * Helper to create a map.
	 * 
	 * @autor          Nikolay Osetrov
	 * @since          0.1.4
	 * @param  <T>
	 * @param  code
	 * @param  message
	 * @return
	 */
	private <T> Map<String, Object> createErrorMap(String code, T message) {
		Map<String, Object> errorMap = new LinkedHashMap<>();
		errorMap.put("code", code);
		errorMap.put("message", message);
		errorMap.put("timestamp", LocalDateTime.now());
		return errorMap;
	}

}
