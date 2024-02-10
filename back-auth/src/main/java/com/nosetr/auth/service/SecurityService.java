package com.nosetr.auth.service;

import com.nosetr.auth.dto.AuthResponseDto;

/**
 * Make handling with authentication and tokens generation.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
public interface SecurityService {

	/**
	 * Begin users login with values from {@link com.nosetr.auth.dto.AuthRequestDto
	 * AuthRequestDto} and return mapped {@link AuthResponseDto}.
	 * 
	 * @autor           Nikolay Osetrov
	 * @since           0.1.0
	 * @param  email    String
	 * @param  password String
	 * @return
	 */
	AuthResponseDto authenticate(String email, String password);
}
