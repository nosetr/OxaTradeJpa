package com.nosetr.library.exception;

import com.nosetr.library.enums.ErrorEnum;

/**
 * {@code EntityNotFoundException} is exception if no entities was found.
 * 
 * <p>Will be handled with
 * {@link ExceptionsHandler#handleEntityNotFoundException(EntityNotFoundException)
 * EntityNotFoundException} to become a 404
 * {@link org.springframework.http.HttpStatus#NOT_FOUND HttpStatus.NOT_FOUND} with
 * message
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 * @see   java.lang.RuntimeException
 * @see   ExceptionsHandler#handleEntityNotFoundException(EntityNotFoundException)
 */
public class EntityNotFoundException extends ApiException {

	private static final long serialVersionUID = -3615634931582495487L;

	/**
	 * Controller with {@link ErrorEnum}
	 * 
	 * @autor           Nikolay Osetrov
	 * @since           0.1.0
	 * @param errorEnum
	 * @param Object... args
	 * @see ErrorEnum
	 */
	public EntityNotFoundException(ErrorEnum errorEnum, Object... args) {
		super(String.format(errorEnum.getMessage(), args), errorEnum.getCode());
	}

}
