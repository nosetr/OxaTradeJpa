package com.nosetr.auth.enums;

import com.nosetr.library.helper.MessageSourceHelper;

import lombok.Getter;

/**
 * Enum for users roles.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.4
 */
public enum UserRoleEnum {
	DEFAULT(
			"USER", "users.role.user"
	),
	USER(
			"USER", "users.role.user"
	),
	ADMIN(
			"ADMIN", "users.role.admin"
	),
	GUEST(
			"GUEST", "users.role.guest"
	);

	@Getter
	private final String labelString;

	private final String message;

	/**
	 * Constructor
	 * 
	 * @autor      Nikolay Osetrov
	 * @since      0.1.4
	 * @param labelString
	 */
	private UserRoleEnum(String label, String message) {
		this.labelString = label;
		this.message = message;
	}

	/**
	 * Get with {@link MessageSourceHelper} translated message.
	 * 
	 * @autor  Nikolay Osetrov
	 * @since  0.1.4
	 * @return String
	 * @see    MessageSourceHelper
	 */
	public String getMessage() { return MessageSourceHelper.getMessage(message); }
}
