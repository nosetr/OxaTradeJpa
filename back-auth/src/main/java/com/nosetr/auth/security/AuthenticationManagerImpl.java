package com.nosetr.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.nosetr.auth.dto.UserDto;
import com.nosetr.auth.service.UserService;
import com.nosetr.library.enums.ErrorEnum;
import com.nosetr.library.util.exception.AuthException;

import lombok.RequiredArgsConstructor;

/**
 * Manager for users validation
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
//@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

	private final UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		// To return not only name, but also id of user
		CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

		UserDto user = userService.getUserById(principal.getId()); // find one

		// Exception if user's account is not active:
		if (user == null || !user.isEnabled()) { throw new AuthException(ErrorEnum.USER_ACCOUNT_IS_DISABLED); }

		// We are returning the same authentication object since it's already authenticated.
		return authentication;
	}
}
