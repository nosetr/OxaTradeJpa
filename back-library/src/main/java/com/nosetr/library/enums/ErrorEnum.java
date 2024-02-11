package com.nosetr.library.enums;

import com.nosetr.library.util.helper.MessageSourceHelper;

import lombok.Getter;

/**
 * Enum for errors.
 * <p>
 * <b>Example:</b>
 * <p>
 * {@code return Mono.error(
							new EntityAlreadyExistsException(ErrorEnum.USER_WITH_EMAIL_ALREADY_EXISTS, email)
					);}
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
public enum ErrorEnum {

	INVALID_EMAIL_IS_REQUESTED(
			"INVALID_EMAIL", "exception.email.invalid"
	),
	INVALID_PASSWORD_IS_REQUESTED(
			"INVALID_PASSWORD", "exception.password.invalid"
	),
	USER_WITH_EMAIL_ALREADY_EXISTS(
			"EMAIL_ALREADY_IN_USE", "exception.user.email.alreadyExists"
	),
	USER_NOT_FOUND(
			"USER_NOT_FOUND", "exception.user.notFound"
	),
	USER_ACCOUNT_IS_DISABLED(
			"USER_ACCOUNT_DISABLED", "exception.user.disabled"
	),
	USER_ROLE_ID_NOT_FOUND(
			"USER_ROLE_NOT_FOUND", "exception.user.role.id.notFound"
	),
	USER_ROLE_NAME_NOT_FOUND(
			"USER_ROLE_NOT_FOUND", "exception.user.role.name.notFound"
	),
	TOKEN_IS_EXPIRED(
			"TOKEN_EXPIRED", "exception.access.tokenExpired"
	),
	NEWS_EMAIL_ALREADY_EXISTS(
			"EMAIL_ALREADY_IN_USE", "exception.newsemail.alreadyExists"
	),
	NEWS_THEMA_NOT_FOUND(
			"NEWS_THEMA_NOT_FOUND", "exception.newsthema.notFound"
	);

	@Getter
	private final String code;

	private final String message;

	/**
	 * Constructor
	 * 
	 * @autor         Nikolay Osetrov
	 * @since         0.1.0
	 * @param code
	 * @param message Translated with {@link MessageSourceHelper}
	 */
	private ErrorEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Get with {@link MessageSourceHelper} translated message.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.0
	 * @return String
	 * @see    MessageSourceHelper
	 */
	public String getMessage() { return MessageSourceHelper.getMessage(message); }
}
