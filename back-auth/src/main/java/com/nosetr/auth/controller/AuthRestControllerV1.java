package com.nosetr.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.nosetr.auth.api.AuthRestApiV1;
import com.nosetr.auth.dto.AuthRequestDto;
import com.nosetr.auth.dto.AuthResponseDto;
import com.nosetr.auth.dto.UserDto;
import com.nosetr.auth.dto.UserRegisterDto;
import com.nosetr.auth.service.SecurityService;
import com.nosetr.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for actions with users authentication.
 * 
 * @autor Nikolay Osetrov
 * @since 0.1.0
 */
@RestController
@RequiredArgsConstructor
public class AuthRestControllerV1 implements AuthRestApiV1 {

	private final SecurityService securityService;
	private final UserService userService;

	/**
	 * Users registration action with requested body.
	 * TODO confirmations link on email must be send before account is enabled.
	 * 
	 * @autor                  Nikolay Osetrov
	 * @since                  0.1.0
	 * @param  userRegisterDto
	 * @return
	 */
	@Override
	public ResponseEntity<UserDto> register(@Valid UserRegisterDto userRegisterDto) {
		return ResponseEntity.ok(userService.registerUser(userRegisterDto));
	}

	/**
	 * Users login action.
	 * <p> Begin of authentication with email and password.
	 * 
	 * @autor                 Nikolay Osetrov
	 * @since                 0.1.0
	 * @param  authRequestDto AuthRequestDto
	 * @return
	 * @see                   AuthRequestDto
	 */
	@Override
	public ResponseEntity<AuthResponseDto> login(@Valid AuthRequestDto authRequestDto) {

		// Authenticate user and retrieve token details synchronously:
		AuthResponseDto authResponseDto = securityService.authenticate(authRequestDto.getEmail(), authRequestDto.getPassword());

		// Return ResponseEntity with the created AuthResponseDto:
		return ResponseEntity.ok(authResponseDto);
	}

}
