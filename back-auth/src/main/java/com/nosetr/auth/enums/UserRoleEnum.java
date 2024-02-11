package com.nosetr.auth.enums;

import com.nosetr.library.util.helper.MessageSourceHelper;

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
	private final String name;

	private final String message;

	/**
	 * Constructor
	 * 
	 * @autor      Nikolay Osetrov
	 * @since      0.1.4
	 * @param name
	 */
	private UserRoleEnum(String name, String message) {
		this.name = name;
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
