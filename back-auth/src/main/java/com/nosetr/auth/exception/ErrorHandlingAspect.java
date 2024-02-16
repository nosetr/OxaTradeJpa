package com.nosetr.auth.exception;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.google.common.base.CaseFormat;
import com.nosetr.library.exception.ApiException;
import com.nosetr.library.exception.AuthException;
import com.nosetr.library.exception.EntityAlreadyExistsException;
import com.nosetr.library.exception.EntityNotFoundException;
import com.nosetr.library.exception.UnauthorizedException;
import com.nosetr.library.exception.UnprocessableEntityException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * Aspect for errors handling.
 * TODO MethodArgumentNotValidException don't work. Aspect is unable.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
@Slf4j
//@Aspect
//@Component
public class ErrorHandlingAspect {

	// Default status
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

//	@AfterThrowing(pointcut = "execution(* com.nosetr.auth.*.*(..))", throwing = "ex")
//  public void handleException(Exception ex) {
//		log.info("+++++++++++ handleException ++++++++");
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpServletResponse response = attr.getResponse();
//      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//      // Weitere Anpassungen und Logging je nach Bedarf
//  }
	
	/**
	 * Return HttpStatus and message for each exception.
	 * 
	 * @autor            Nikolay Osetrov
	 * @since            0.1.4
	 * @param  joinPoint
	 * @return
	 * @throws Throwable
	 */
//	@Around("execution (* com.nosetr.auth.controller.impl.*Controller*.*(..))")
	public Object handleControllerException(ProceedingJoinPoint joinPoint) throws Throwable {

		ArrayList<Map<String, Object>> errorList = new ArrayList<>();
		log.info("+++++++++++ ASPECT START ++++++++");
		try {
			log.info("+++++++++++ ASPECT-TRY ++++++++");
			return joinPoint.proceed();
		} catch (AuthException | UnauthorizedException ex) {
			// UNAUTHORIZED
			log.error("UNAUTHORIZED: ", ex);
			status = HttpStatus.UNAUTHORIZED;
			errorList.add(createErrorMap(ex.getErrorCode(), ex.getMessage()));
		} catch (ExpiredJwtException | SignatureException | MalformedJwtException ex) {
			// UNAUTHORIZED JwtException
			log.error("UNAUTHORIZED JwtException: ", ex);
			status = HttpStatus.UNAUTHORIZED;
			errorList.add(createErrorMap("UNAUTHORIZED", ex.getMessage()));
		} catch (EntityNotFoundException ex) {
			// NOT_FOUND
			log.error("NOT_FOUND: ", ex);
			status = HttpStatus.NOT_FOUND;
			errorList.add(createErrorMap(((ApiException) ex).getErrorCode(), ex.getMessage()));
		} catch (EntityAlreadyExistsException ex) {
			// CONFLICT
			log.error("CONFLICT: ", ex);
			status = HttpStatus.CONFLICT;
			errorList.add(createErrorMap(((ApiException) ex).getErrorCode(), ex.getMessage()));
		} catch (MethodArgumentNotValidException | UnprocessableEntityException ex) {
			// UNPROCESSABLE_ENTITY
			log.error("UNPROCESSABLE_ENTITY: ", ex);
			status = HttpStatus.UNPROCESSABLE_ENTITY;
			errorList.add(createErrorMap("VALIDATION_FAILED", ex.getMessage()));
		} 
//		catch (MethodArgumentNotValidException ex) {
//			// UNPROCESSABLE_ENTITY
//			log.error("UNPROCESSABLE_ENTITY: ", ex);
//			status = HttpStatus.UNPROCESSABLE_ENTITY;
//			errorList.add(createErrorMap("VALIDATION_FAILED", "Validation failed for input fields"));
//			ex.getBindingResult()
//					.getAllErrors()
//					.forEach(error -> {
//						String fieldName = ((FieldError) error).getField();
//						String errorMessage = error.getDefaultMessage();
//						// Hier können Sie weitere Informationen zur Fehlerbehandlung hinzufügen, z. B. den Feldnamen und die Fehlermeldung
//					});
//		}
		catch (WebExchangeBindException ex) {
			// UNPROCESSABLE_ENTITY "VALIDATION_FAILED" of fields
			log.error("UNPROCESSABLE_ENTITY \"VALIDATION_FAILED\" of fields: ", ex);
			status = HttpStatus.UNPROCESSABLE_ENTITY;
			// Fields errors:
			Map<String, String> errors = new HashMap<>();
			BindingResult result = ex.getBindingResult();
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

			errorList.add(createErrorMap("FIELD_VALIDATION_FAILED", errors));
		} catch (ApiException ex) {
			// BAD_REQUEST
			log.error("BAD_REQUEST: ", ex);
			status = HttpStatus.BAD_REQUEST;
			errorList.add(createErrorMap(ex.getErrorCode(), ex.getMessage()));
		} catch (Exception ex) {
			// other
			log.error("other: ", ex);
			String message = ex.getMessage();
			if (message == null)
				message = ex.getClass()
						.getName();
			status = (message.equals("404 NOT_FOUND")) ? HttpStatus.NOT_FOUND
					: HttpStatus.INTERNAL_SERVER_ERROR;

			errorList.add(
					createErrorMap(
							(message.equals("404 NOT_FOUND")) ? "NOT_FOUND"
									: "INTERNAL_ERROR", message
					)
			);
		}

		log.info("+++++++++++ ASPECT FINISH ++++++++");
		HashMap<String, Object> errors = new HashMap<>();
		errors.put("errors", errorList);
		return new ResponseEntity<>(errors, status);
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
		errorMap.put("timestamp", LocalDateTime.now());
		return errorMap;
	}
}
